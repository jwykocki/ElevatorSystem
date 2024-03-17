package com.elevators.infrastructure;

import com.elevators.domain.dto.PickupRequestDto;
import com.elevators.domain.ElevatorSystem;
import com.elevators.domain.dto.ElevatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
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
    public ResponseEntity<Integer> pickup(@RequestBody PickupRequestDto pickupRequestDto) {
        return ResponseEntity.ok(elevatorSystem.pickup(pickupRequestDto.floor()));
    }

    @PostMapping("/elevators/step")
    public void makeStep() {
        elevatorSystem.step();
    }


}
