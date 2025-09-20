# 📧 Email Scheduler System

Hệ thống **Email Scheduler** giúp quản lý và tự động gửi email (hàng loạt hoặc theo lịch trình) cho nhân viên/khách hàng.  
Ứng dụng được xây dựng trên nền tảng **Spring Boot**, hỗ trợ **quản lý template email**, **người dùng**, **lập lịch gửi email (cron job)** và **theo dõi trạng thái**.

---

## 🚀 Tính năng chính
- ✅ Quản lý người dùng (thêm/sửa/xóa).
- ✅ Quản lý template email (HTML template động).
- ✅ Quản lý lịch gửi email (ngày, tuần, tháng, hoặc theo cron expression).
- ✅ Hỗ trợ gửi email hàng loạt.
- ✅ Lưu log gửi email.
- ✅ Hỗ trợ giao diện quản trị bằng **Thymeleaf + Bootstrap**.
- ✅ Exception Handling toàn cục (API JSON + giao diện HTML).

---

## 🛠️ Công nghệ sử dụng
- **Java 17+**
- **Spring Boot 3+**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Mail
  - Spring Scheduler
- **Thymeleaf** (render giao diện web)
- **MySQL** (cơ sở dữ liệu)
- **Lombok** (giảm boilerplate code)
- **Logback** (logging)
- **JUnit + Mockito** (unit test)
- **Docker** (chạy môi trường production/dev)

---

## 📂 Cấu trúc thư mục chính
```bash
src/main/java/com/example/emailscheduler
│── controller/        # Xử lý request, mapping Thymeleaf view
│── dto/               # DTOs (Form data)
│── entity/            # Entity JPA
│── exception/         # Xử lý Exception toàn cục
│── repository/        # Repository JPA
│── scheduler/         # Job Scheduler
│── service/           # Business logic
│── util/              # Tiện ích (validator, helper)
│── EmailSchedulerApplication.java

⚙️ Cấu hình
1. Application Properties
spring.application.name=email-scheduler

# --- Server ---
server.port=8080
server.address=0.0.0.0

# --- Database ---
# Ưu tiên lấy từ ENV (docker-compose), nếu không có thì fallback về local MySQL
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/email_scheduler?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:password}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# --- JPA / Hibernate ---
spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:update}
spring.jpa.show-sql=${SPRING_JPA_SHOW_SQL:true}
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect


# --- Spring Mail (Gmail example) ---
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_gmail
spring.mail.password=your_app_password
spring.mail.protocol=smtp
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
spring.mail.default-encoding=UTF-8

# --- Logging ---
logging.level.org.springframework=INFO
logging.level.com.example.emailscheduler=DEBUG

# Log ra file
logging.file.name=logs/email-scheduler.log
logging.file.path=logs
2. Docker

Chạy ứng dụng bằng Docker Compose:

docker-compose up -d

▶️ Chạy ứng dụng
1. Chạy trực tiếp
./mvnw spring-boot:run

2. Truy cập

Web UI: http://localhost:8080

API (REST): http://localhost:8080/api/...

🧪 Unit Test

Chạy toàn bộ test:

./mvnw test
mvn -Dtest=ServiceTest test
