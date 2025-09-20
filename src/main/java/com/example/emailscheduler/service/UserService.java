package com.example.emailscheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.emailscheduler.entity.User;
import com.example.emailscheduler.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Service chịu trách nhiệm xử lý business logic liên quan đến User.
 *
 * - Lấy danh sách user
 * - Tìm user theo ID
 * - Tạo mới user (check trùng email)
 * - Cập nhật user (check trùng email khi thay đổi)
 * - Xóa user
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Lấy tất cả người dùng.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Tìm user theo ID.
     *
     * @throws IllegalArgumentException nếu không tìm thấy
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    /**
     * Tạo mới user, kiểm tra email trùng lặp trước khi lưu.
     *
     * @throws IllegalArgumentException nếu email đã tồn tại
     */
    @Transactional
    public User create(User user) {
        validateEmailUnique(user.getEmail(), null);
        return userRepository.save(user);
    }

    /**
     * Cập nhật user theo ID.
     * Nếu email thay đổi → kiểm tra trùng lặp.
     *
     * @throws IllegalArgumentException nếu không tìm thấy user hoặc email đã tồn tại
     */
    @Transactional
    public User update(Long id, User newUser) {
        User existing = findById(id);

        if (!existing.getEmail().equals(newUser.getEmail())) {
            validateEmailUnique(newUser.getEmail(), id);
        }

        existing.setName(newUser.getName());
        existing.setEmail(newUser.getEmail());
        existing.setStatus(newUser.getStatus());

        return userRepository.save(existing);
    }

    /**
     * Xóa user theo ID.
     */
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Helper method: kiểm tra email trùng lặp.
     *
     * @param email email cần kiểm tra
     * @param excludeId bỏ qua ID (khi update)
     */
    private void validateEmailUnique(String email, Long excludeId) {
        userRepository.findByEmail(email).ifPresent(user -> {
            if (excludeId == null || !user.getId().equals(excludeId)) {
                throw new IllegalArgumentException("Email already exists: " + email);
            }
        });
    }
}