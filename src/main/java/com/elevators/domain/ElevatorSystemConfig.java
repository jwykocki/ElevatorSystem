package com.elevators.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@EnableConfigurationProperties
@ConfigurationProperties(prefix = "elevator.system")
@Getter
@Setter
public class ElevatorSystemConfig {
    private int numberOfElevators;
    private int minimumFloor;
    private int maximumFloor;

    public ElevatorSystemConfig(int numberOfElevators, int minimumFloor, int maximumFloor) {
        this.numberOfElevators = numberOfElevators;
        this.minimumFloor = minimumFloor;
        this.maximumFloor = maximumFloor;
    }
}
