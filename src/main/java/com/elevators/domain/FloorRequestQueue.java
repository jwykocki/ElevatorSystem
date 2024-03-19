package com.elevators.domain;

interface FloorRequestQueue {

    void addRequest(int floor, int direction);
    int getNextFloor(int currentFloor);
    int getDistance(int currentFloor, int destinationFloor);

}
