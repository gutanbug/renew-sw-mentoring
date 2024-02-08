package com.renew.sw.mentoring.domain.user.repository;

import com.renew.sw.mentoring.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.studentId = :studentId")
    Optional<User> findByStudentId(@Param("studentId") String studentId);
}
