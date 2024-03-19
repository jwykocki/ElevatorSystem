package com.elevators.domain;

import com.elevators.domain.dto.ElevatorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class ElevatorSystem {

    private final List<Elevator> elevators;
    private final List<FloorRequestQueue> floorQueues;
    public ElevatorSystem(ElevatorSystemConfig config) {
        elevators = new ArrayList<>();
        floorQueues = new ArrayList<>();
        for (int id = 0; id < config.getNumberOfElevators(); id++) {
            elevators.add(Elevator.builder()
                    .id(id)
                    .currentFloor(0)
                    .nextFloor(0)
                    .build());
            floorQueues.add(new SimpleScanRequestQueue(config.getMinimumFloor(), config.getMaximumFloor()));
        }
        log.info("Created system with " + config.getNumberOfElevators() + " elevators");


    }

    public List<ElevatorDto> status(){

        List<ElevatorDto> elevatorsStatus = new ArrayList<>();

        for(Elevator elevator : elevators){
            ElevatorDto currentElevatorStatus = ElevatorDto.builder()
                    .id(elevator.getId())
                    .currentFloor(elevator.getCurrentFloor())
                    .nextFloor(elevator.getNextFloor())
                    .build();

            elevatorsStatus.add(currentElevatorStatus);
        }

        return elevatorsStatus;
    }

    private void update(int id, int currentFloor, int nextFloor){
        elevators.get(id).setCurrentFloor(currentFloor);
        elevators.get(id).setNextFloor(nextFloor);
    }

    public void step(){
        log.info("- Step -");
        for(Elevator elevator : elevators){
            int newCurrentFloor = elevator.getNextFloor();
            int newNextFloor = floorQueues.get(elevator.getId()).getNextFloor(newCurrentFloor);
            update(elevator.getId(), newCurrentFloor, newNextFloor);
        }
    }

    private int countDirection(int currentFloor, int destinationFloor){
        if(currentFloor>destinationFloor){
            return  -1;
        }else{
            return  1;
        }
    }

    private int findNearestElevator(int floor) {
        int nearestElevator = 0;
        int minDistance = Math.abs(floorQueues.get(0).getDistance(elevators.get(0).getCurrentFloor(), floor));
        log.info("Find nearest elevator: " + minDistance + "(" + nearestElevator + ")");
        for (int i = 1; i < elevators.size(); i++) {
            int distance = Math.abs(floorQueues.get(i).getDistance(elevators.get(i).getCurrentFloor(), floor));
            log.info("Find nearest elevator: " + distance+ "(" + elevators.get(i).getId() + ")");
            if (distance < minDistance) {

                minDistance = distance;
                log.info("new min = " + minDistance);
                nearestElevator = i;
            }
        }
        log.info("return " + nearestElevator);
        return nearestElevator;
    }

    public int pickup(int floor){
        int elevatorIndex = findNearestElevator(floor);
        int currentFloor = elevators.get(elevatorIndex).getCurrentFloor();
        int direction = countDirection(currentFloor, floor);
        log.info("Pickup: " + floor + " " + direction + "(" + elevatorIndex + ")");
        floorQueues.get(elevatorIndex).addRequest(floor, direction);
        return elevatorIndex;
    }
}
