package api.afrilangue;

import api.afrilangue.models.Language;
import api.afrilangue.models.Role;
import api.afrilangue.repositories.LanguageRepository;
import api.afrilangue.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import static api.afrilangue.models.ERole.*;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
public class AfrilangueApplication {
    private static final Logger log = LoggerFactory.getLogger(AfrilangueApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AfrilangueApplication.class, args);
    }

    @Bean
    public CommandLineRunner addRole(RoleRepository repository, LanguageRepository languageRepository) {
        return (args) -> {
            if (!repository.existsRoleByName(ROLE_USER)) repository.save(Role.builder().name(ROLE_USER).build());
            if (!repository.existsRoleByName(ROLE_ADMIN)) repository.save(Role.builder().name(ROLE_ADMIN).build());
            if (!repository.existsRoleByName(ROLE_TEACHER)) repository.save(Role.builder().name(ROLE_TEACHER).build());
            if(!languageRepository.existsLanguageByNameIgnoreCase("Français")) languageRepository.save(Language.builder().name("Français").build());

            log.info("Add successfully!!!");
        };

    }

}
