package domain;

public interface FloorRequestQueue {

    void addRequest(int floor, int direction) throws Exception;
    int getNextFloor(int currentFloor);

}
