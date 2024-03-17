package com.elevators.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
public record  PickupRequestDto (
        @NotNull(message = "{floorNumber.not.null}")
        @NotEmpty(message = "{floorNumber.not.null}")
        int floor
) {

}
