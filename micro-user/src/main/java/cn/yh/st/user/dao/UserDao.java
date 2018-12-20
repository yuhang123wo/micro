package cn.yh.st.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.yh.st.user.domain.User;

public interface UserDao extends JpaRepository<User, Long> {

}
