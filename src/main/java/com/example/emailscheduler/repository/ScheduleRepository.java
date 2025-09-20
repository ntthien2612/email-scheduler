package com.example.emailscheduler.repository;

import com.example.emailscheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository quản lý entity {@link Schedule}.
 *
 * Kế thừa JpaRepository đã có sẵn CRUD:
 * - save, findById, findAll, deleteById, existsById...
 *
 * Bổ sung method custom cho Schedule.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * Kiểm tra template có được sử dụng trong Schedule không.
     *
     * @param templateId id của EmailTemplate
     * @return true nếu có tồn tại schedule
     */
    boolean existsByTemplateId(Long templateId);

    /**
     * Lấy danh sách schedule theo trạng thái.
     */
    List<Schedule> findByStatus(Schedule.Status status);

    /**
     * Đếm số schedule đang tham chiếu đến một template.
     */
    long countByTemplate_Id(Long templateId);

    /**
     * Lấy danh sách schedule + template (eager fetch)
     * để tránh N+1 query problem.
     */
    @Query("SELECT s FROM Schedule s JOIN FETCH s.template WHERE s.status = :status")
    List<Schedule> findByStatusWithTemplate(@Param("status") Schedule.Status status);
}
