package cn.st.axon;

public class ToDoItemCreatedEvent {
	private ToDoItem toDoItem;

	public ToDoItemCreatedEvent(ToDoItem toDoItem) {
		this.toDoItem = toDoItem;
	}

	public ToDoItem getToDoItem() {
		return toDoItem;
	}
}
