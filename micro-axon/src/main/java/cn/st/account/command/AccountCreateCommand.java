package cn.st.account.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;




public class AccountCreateCommand {

	@TargetAggregateIdentifier
	private String accountId;

	public AccountCreateCommand(String accountId) {
		this.accountId = accountId;
		System.out.println(this.getAccountId()+":xxxxxxxx");
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

}
