package cn.st.account.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;



public class AccountDepositCommand {

	@TargetAggregateIdentifier
	private String accountId;
	private int amount;

	public AccountDepositCommand(String accountId, int amount) {
		this.accountId = accountId;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
