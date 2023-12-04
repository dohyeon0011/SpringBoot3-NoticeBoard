package org.example.springbootdeveloper.repository;

import org.example.springbootdeveloper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // email로 사용자 정보를 가져옴
    // from users where email = #{email}
    Optional<User> findByEmail(String email);

}
