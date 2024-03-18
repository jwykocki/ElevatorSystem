package com.elevators.infrastructure;

import com.elevators.domain.dto.PickupRequestDto;
import com.elevators.domain.ElevatorSystem;
import com.elevators.domain.dto.ElevatorDto;
import com.elevators.domain.dto.PickupResponseDto;

import com.elevators.domain.exceptions.IncorrectFloorNumberException;
import com.elevators.domain.exceptions.IncorrectPickupRequestFormat;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Log4j2
@Validated
public class ElevatorController {

    private final ElevatorSystem elevatorSystem;

    @Autowired
    public ElevatorController(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    @GetMapping("/elevators/status")
    public ResponseEntity<List<ElevatorDto>> getElevators() {
        return ResponseEntity.ok(elevatorSystem.status());
    }

    @PostMapping("/elevators/pickup")
    public ResponseEntity<PickupResponseDto> pickup(@RequestBody @Valid PickupRequestDto pickupRequestDto) {
        if(pickupRequestDto.floor() == null) {
            throw new IncorrectPickupRequestFormat();
        }
        PickupResponseDto response = PickupResponseDto.builder()
                .elevatorId(elevatorSystem.pickup(pickupRequestDto.floor()))
                .build();
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/elevators/step")
    public void makeStep() {
        elevatorSystem.step();
    }


}
