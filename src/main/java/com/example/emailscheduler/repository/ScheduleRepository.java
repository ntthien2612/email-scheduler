package com.example.emailscheduler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.emailscheduler.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByTemplateId(Long templateId);
    List<Schedule> findByStatus(Schedule.Status status);
    long countByTemplate_Id(Long templateId);
    List<Schedule> findAll();
    boolean existsById(Long id);
    Schedule findById(long id);
    void deleteById(Long id);
    @Query("SELECT s FROM Schedule s JOIN FETCH s.template WHERE s.status = :status")
    List<Schedule> findByStatusWithTemplate(@Param("status") Schedule.Status status);

}
