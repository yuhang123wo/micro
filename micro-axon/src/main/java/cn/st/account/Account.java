package cn.st.account;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import cn.st.account.command.AccountCreateCommand;
import cn.st.account.command.AccountDepositCommand;
import cn.st.account.command.AccountWithDrawCommand;
import cn.st.account.event.AccountCreatedEvent;
import cn.st.account.event.AccountDepositedEvent;
import cn.st.account.event.AccountWithdrawedEvent;

@Aggregate
// 表明是聚合对象
@Entity(name = "t_account")
public class Account {

	@Id
	// 表明是聚合对象的ID
	private String accountId;
	private int deposit;

	public Account() {
	}

	@CommandHandler
	public Account(AccountCreateCommand command) {
		AggregateLifecycle.apply(new AccountCreateCommand(command.getAccountId()));
	}

	@CommandHandler
	public void handle(AccountDepositCommand command) {
		AggregateLifecycle.apply(new AccountDepositCommand(command.getAccountId(), command.getAmount()));
	}

	@CommandHandler
	public void withdraw(AccountWithDrawCommand command) {
		if (this.deposit >= command.getAmount()) {
			AggregateLifecycle.apply(new AccountWithDrawCommand(command.getAccountId(), command.getAmount()));
		} else {
			throw new IllegalArgumentException("余额不足");
		}
	}

	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		this.accountId = event.getAccountId();
		this.deposit = 0;
	}

	@EventSourcingHandler
	public void on(AccountDepositedEvent event) {
		this.deposit += event.getAmount();
	}

	@EventSourcingHandler
	public void on(AccountWithdrawedEvent event) {
		this.deposit -= event.getAmount();
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

}
