package vn.edu.fpt.authentication.config.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import vn.edu.fpt.authentication.config.security.annotation.auditor.SecurityAuditorAware;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:20
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Configuration
@EnableMongoAuditing(
        auditorAwareRef = "auditorAware",
        dateTimeProviderRef = "utcDateTimeProvider"
)
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new SecurityAuditorAware();
    }

    @Bean
    public DateTimeProvider utcDateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}
