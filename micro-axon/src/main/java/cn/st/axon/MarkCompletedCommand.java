package cn.st.axon;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

//标记待办事项完成

//命令对象代表了用户可以做两种操作，来改变数据状态
public class MarkCompletedCommand {
	@TargetAggregateIdentifier
	private final String todoId;

	public MarkCompletedCommand(String todoId) {
		this.todoId = todoId;
	}

	public String getTodoId() {
		return todoId;
	}
}
