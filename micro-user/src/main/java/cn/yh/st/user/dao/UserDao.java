package cn.yh.st.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.yh.st.user.domain.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findOneById(Long id);

	@Modifying
	@Query("update t_user set deposit=deposit-?2 where id=?1")
	int charge(Long customerId, int amount);
}
