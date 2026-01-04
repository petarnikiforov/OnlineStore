package org.online.store;
import org.online.store.enums.Gender;
import org.online.store.enums.Role;
import org.online.store.models.Userr;
import org.online.store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class,args);
        System.out.println("Application started successfully!");
    }
    @Bean
    CommandLineRunner seedAdmin(UserRepository userRepo) {
        return args -> {
            boolean hasAdmin = userRepo.findAll().stream().anyMatch(u -> u.getRole() == Role.ROLE_ADMIN);
            if (!hasAdmin) {
                Userr admin = Userr.builder()
                        .username("admin")
                        .password("admin123") // по-добре BCrypt
                        .role(Role.ROLE_ADMIN)
                        .email("admin@pmarket.bg")
                        .fullName("System Admin")
                        .gender(Gender.MALE)
                        .build();
                userRepo.save(admin);
            }
        };
    }
}