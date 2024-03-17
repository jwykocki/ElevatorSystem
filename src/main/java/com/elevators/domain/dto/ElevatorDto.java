package com.elevators.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
public class ElevatorDto implements Serializable{
    private int id;
    private int currentFloor;
    private int nextFloor;

    @Override
    public String toString() {
        return "[" +
                id +
                ", " + currentFloor +
                ", " + nextFloor +
                ']';
    }
}
