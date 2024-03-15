package elevators.domain;

import elevators.domain.exceptions.IncorrectFloorNumberException;

public interface FloorRequestQueue {

    void addRequest(int floor, int direction);
    int getNextFloor(int currentFloor);
    int getDistance(int currentFloor, int destinationFloor);

}
