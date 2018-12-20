package cn.yh.st.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.yh.st.user.domain.PayInfo;

public interface PayDao extends JpaRepository<PayInfo, Long> {

	PayInfo findByOrderId(Long orderId);
}
