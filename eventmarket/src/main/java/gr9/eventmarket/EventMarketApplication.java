package gr9.eventmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EventMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventMarketApplication.class, args);
    }
}


