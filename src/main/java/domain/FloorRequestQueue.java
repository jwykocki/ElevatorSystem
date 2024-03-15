package domain;

public interface FloorRequestQueue {

    void addRequest(int floor, int direction) throws IncorrectFloorNumberException;
    int getNextFloor(int currentFloor);

    int getDistance(int currentFloor, int destinationFloor);

}
