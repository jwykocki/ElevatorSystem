package domain;

public interface FloorRequestQueue {

    void addRequest(int currentFloor, int floor);
    int getNextFloor(int currentFloor);

}
