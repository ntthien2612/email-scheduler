package com.example.emailscheduler.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emailscheduler.entity.User;

/**
 * Repository quản lý entity {@link User}.
 * 
 * Kế thừa từ JpaRepository:
 * - Có sẵn CRUD cơ bản: findById, findAll, save, deleteById...
 * - Có sẵn kiểm tra tồn tại: existsById
 * 
 * Bổ sung query method custom cho User.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Tìm user theo email.
     *
     * @param email địa chỉ email
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * Kiểm tra email đã tồn tại trong hệ thống chưa.
     *
     * @param email địa chỉ email
     * @return true nếu tồn tại
     */
    boolean existsByEmail(String email);
}