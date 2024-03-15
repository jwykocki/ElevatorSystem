package domain;

public interface FloorRequestQueue {

    void addRequest(int floor, int direction);
    int getNextFloor(int currentFloor);

}
