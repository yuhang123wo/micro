package cn.st.axon;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

//一个是创建待办事项
//命令对象代表了用户可以做两种操作，来改变数据状态
public class CreateToDoItemCommand {

	@TargetAggregateIdentifier
	private final String todoId;
	private final String remark;

	public CreateToDoItemCommand(String todoId, String remark) {
		this.todoId = todoId;
		this.remark = remark;
	}

	public String getTodoId() {
		return todoId;
	}

	public String getRemark() {
		return remark;
	}
}
