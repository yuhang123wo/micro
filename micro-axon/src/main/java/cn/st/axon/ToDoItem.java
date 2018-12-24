package cn.st.axon;

import javax.persistence.Id;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ToDoItem {
	@Id
	private String id;
	private String remark;
	private int status;

	public ToDoItem() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public final static Integer STATUS_UN_COMPLETE = 1;
	public final static Integer STATUS_COMPLETE = 2;

	/**
	 * / 处理命令
	 */
	@CommandHandler
	public ToDoItem(CreateToDoItemCommand command) {
		// 根据命令创建实体
		setId(command.getTodoId());
		setRemark(command.getRemark());
		setStatus(ToDoItem.STATUS_UN_COMPLETE);
		// 事件产生
		AggregateLifecycle.apply(new ToDoItemCreatedEvent(this));
	}

	/**
	 * / 处理命令
	 */
	@CommandHandler
	public void markCompleted(MarkCompletedCommand command) {
		if (getStatus() == ToDoItem.STATUS_UN_COMPLETE) {
			setStatus(ToDoItem.STATUS_COMPLETE);
			AggregateLifecycle.apply(new ToDoItemCompletedEvent(id));
		} else {
			// 抛出异常处理
		}
	}

	/**
	 * / 处理事件
	 */
	@EventHandler
	public void onCreate(ToDoItemCreatedEvent event) {
		System.out.println("待办事项" + event.getToDoItem().getRemark() + "创建成功");
	}

	/**
	 * / 处理事件
	 */
	@EventHandler
	public void onMarkComplete(ToDoItemCompletedEvent event) {
		System.out.println("待办事项" + event.getTodoId() + "已完成");
	}
}
