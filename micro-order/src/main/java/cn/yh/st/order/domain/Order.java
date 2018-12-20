package cn.yh.st.order.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "t_order")
public class Order {

	@Id
	@GeneratedValue
	private Long id;
	private String uuid;
	private Long cusomerId;
	private String title;
	private Long ticketNum;
	private int amount;
	private String status;
	private String reason;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getCusomerId() {
		return cusomerId;
	}

	public void setCusomerId(Long cusomerId) {
		this.cusomerId = cusomerId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", uuid=" + uuid + ", cusomerId=" + cusomerId + ", title="
				+ title + ", ticketNum=" + ticketNum + ", amount=" + amount + ", status=" + status
				+ ", reason=" + reason + "]";
	}

}
