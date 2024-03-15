package domain;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class ElevatorSystem {
    public ElevatorSystem(int numberOfElevators) {
        elevators = new ArrayList<>();
        floorQueues = new ArrayList<>();
        for (int id = 0; id < numberOfElevators; id++) {
            elevators.add(new Elevator(id, 0, 0));
            floorQueues.add(new SimpleScanRequestQueue());
        }
        log.info("Created system with " + numberOfElevators + " elevators");
    }

    private final List<Elevator> elevators;
    private final List<FloorRequestQueue> floorQueues;


    public List<List<Integer>> status(){

        List<List<Integer>> elevatorsStatus = new ArrayList<>();

        for(Elevator elevator : elevators){
            List<Integer> currentElevatorStatus = new ArrayList<>();
            currentElevatorStatus.add(elevator.getId());
            currentElevatorStatus.add(elevator.getCurrentFloor());
            currentElevatorStatus.add(elevator.getNextFloor());
            elevatorsStatus.add(currentElevatorStatus);
        }

        return elevatorsStatus;
    }

    public void printStatus(){
        List<List<Integer>> elevatorsStatus = status();
        for (List<Integer> status : elevatorsStatus) {
            int id = status.get(0);
            int currentFloor = status.get(1);
            int nextFloor = status.get(2);
            System.out.println("Elevator " + id + ": " + currentFloor + " " + nextFloor);
        }
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

    public int pickup(int floor){

        int elevatorIndex = 0; //TODO: find nearest elevator
        int currentFloor = elevators.get(elevatorIndex).getCurrentFloor();
        int direction;
        if(currentFloor>floor){
            direction = -1;
        }else{
            direction = 1;
        }
        log.info("Pickup: " + floor + " " + direction);
        floorQueues.get(elevatorIndex).addRequest(floor, direction);
        return elevatorIndex;
    }
}
