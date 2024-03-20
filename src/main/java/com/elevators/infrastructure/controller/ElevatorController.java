package com.elevators.infrastructure.controller;

import com.elevators.domain.dto.PickupRequestDto;
import com.elevators.domain.ElevatorSystem;
import com.elevators.domain.dto.ElevatorDto;
import com.elevators.domain.dto.PickupResponseDto;
import com.elevators.domain.exceptions.IncorrectPickupRequestFormat;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
@Validated
@RequestMapping("/elevators")
public class ElevatorController {

    private final ElevatorSystem elevatorSystem;

    @Autowired
    public ElevatorController(ElevatorSystem elevatorSystem) {
        this.elevatorSystem = elevatorSystem;
    }

    @GetMapping("/status")
    public ResponseEntity<List<ElevatorDto>> getElevators() {
        log.info("Received request - GET '/elevators/status'");
        return ResponseEntity.ok(elevatorSystem.status());
    }

    @PostMapping("/pickup")
    public ResponseEntity<PickupResponseDto> pickup(@RequestBody @Valid PickupRequestDto pickupRequestDto) {
        log.info("Received request - POST '/elevators/pickup'");
        if(pickupRequestDto.floor() == null) {
            throw new IncorrectPickupRequestFormat();
        }
        PickupResponseDto response = PickupResponseDto.builder()
                .elevatorId(elevatorSystem.pickup(pickupRequestDto.floor()))
                .build();
        log.info(response.toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/step")
    public void makeStep() {
        log.info("Received request - POST '/elevators/step'");
        elevatorSystem.step();
    }


}
