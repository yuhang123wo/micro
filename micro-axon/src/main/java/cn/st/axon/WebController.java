package cn.st.axon;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

	@RequestMapping("create")
	public void testCreateToDoItem() throws Exception {
		ToDoItem testEntity = new ToDoItem();
		testEntity.setId("todo1");
		testEntity.setRemark("8点钟要上班");
		testEntity.setStatus(1);
	}

	@RequestMapping("comp")
	public void testMarkToDoItemAsCompleted() throws Exception {
		ToDoItem testEntity = new ToDoItem();
		testEntity.setId("todo1");
		testEntity.setRemark("8点钟要上班");
		testEntity.setStatus(1);
	}
}
