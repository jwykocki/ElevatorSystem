package domain;

import java.util.List;

public class Elevator {
    private int id;
    private int currentFloor;
    private int nextFloor;

    public Elevator(int id, int currentFloor, int nextFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.nextFloor = nextFloor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getNextFloor() {
        return nextFloor;
    }

    public void setNextFloor(int nextFloor) {
        this.nextFloor = nextFloor;
    }

}
