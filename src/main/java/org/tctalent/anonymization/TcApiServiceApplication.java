package org.tctalent.anonymization;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.tctalent.anonymization.logging.LogBuilder;

@Slf4j
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class TcApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcApiServiceApplication.class, args);
    }

  /**
   * This is only created if spring.flyway.repair = true in the application.yml config.
   * <p/>
   * It runs a Flyway repair before doing the Flyway processing - ie calling "migrate".
   * <p/>
   * {@link org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer} will
   * run the migrate method of a FlywayMigrationStrategy if one exists, otherwise it will
   * just run Flyway.migrate.
   * @return A FlywayMigrationStrategy bean
   */
  @Bean
  @ConditionalOnProperty(name="spring.flyway.repair", havingValue="true")
  public FlywayMigrationStrategy fixFlyway() {
    return new FlywayMigrationStrategy() {
      @Override
      public void migrate(Flyway flyway) {
        try {
          System.out.println("************* Starting flyway repair ***********************");
          flyway.repair();
          System.out.println("************* Finished flyway repair ***********************");
          flyway.migrate();
        } catch (Exception e) {
          System.out.println("ERROR: unable to repair flyway");
          LogBuilder.builder(log)
              .action("flyway_repair_failed")
              .message("Unable to repair flyway before migration")
              .logError(e);
        }
      }
    };
  }
}
