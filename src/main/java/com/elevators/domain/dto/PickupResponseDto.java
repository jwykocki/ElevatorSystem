package com.elevators.domain.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PickupResponseDto(
        int elevatorId

) implements Serializable{
//    public PickupResponseDto(int elevatorId) {
//        this.elevatorId = elevatorId;
//    }
}
