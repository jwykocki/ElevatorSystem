package elevators;

import elevators.domain.ElevatorSystem;
import elevators.domain.ElevatorSystemConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(ElevatorSystemConfig.class)
public class ElevatorApplication {
    public static void main(String[] args){
        SpringApplication.run(ElevatorApplication.class, args);
    }
}
