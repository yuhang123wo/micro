package cn.st.account.web;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.st.account.command.AccountCreateCommand;
import cn.st.account.command.AccountDepositCommand;
import cn.st.account.command.AccountWithDrawCommand;

@RestController
@RequestMapping("account")
public class AccountController {

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping("create")
	public CompletableFuture<Object> create() {
		UUID accountId = UUID.randomUUID();
		AccountCreateCommand command = new AccountCreateCommand(accountId.toString());
		return commandGateway.send(command);
	}

	@RequestMapping("/{accountId}/deposit/{amount}")
	public CompletableFuture<Object> deposit(@PathVariable String accountId,
			@PathVariable int amount) {
		AccountDepositCommand command = new AccountDepositCommand(accountId, amount);
		return commandGateway.send(command);
	}

	@RequestMapping("/{accountId}/withdraw/{amount}")
	public CompletableFuture<Object> withdraw(@PathVariable String accountId,
			@PathVariable int amount) {
		AccountWithDrawCommand command = new AccountWithDrawCommand(accountId, amount);
		return commandGateway.send(command);
	}
}
