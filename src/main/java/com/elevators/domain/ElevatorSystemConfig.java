package com.elevators.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "elevator.system")
@Getter
@Setter
public class ElevatorSystemConfig {
    private final int numberOfElevators;
    private final int minimumFloor;
    private final int maximumFloor;

    public ElevatorSystemConfig(int numberOfElevators, int minimumFloor, int maximumFloor) {
        this.numberOfElevators = numberOfElevators;
        this.minimumFloor = minimumFloor;
        this.maximumFloor = maximumFloor;
    }
}
