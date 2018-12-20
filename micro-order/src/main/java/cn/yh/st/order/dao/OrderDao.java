package cn.yh.st.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.yh.st.order.domain.Order;

public interface OrderDao extends JpaRepository<Order, Long> {

	List<Order> findByCustomerId(Long customerId);

	Order findOneByUuid(String uuid);
}
