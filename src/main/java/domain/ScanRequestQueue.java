package domain;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;

import java.util.PriorityQueue;
import java.util.Queue;

@Log4j2
public class ScanRequestQueue implements FloorRequestQueue {
    private Queue<Integer> upQueue;
    private Queue<Integer> downQueue;
    private boolean movingUp;

    public ScanRequestQueue() {
        this.upQueue = new PriorityQueue<>();
        this.downQueue = new PriorityQueue<>((a, b) -> b - a);
        this.movingUp = true;
    }

    public void addRequest(int floor, int direction) {
        if (direction > 0) {
            upQueue.offer(floor);
        } else if (direction < 0) {
            downQueue.offer(floor);
        }
    }

    public int getNextFloor(int currentFloor) {
        if(upQueue.isEmpty() && downQueue.isEmpty()){
            return currentFloor;
        }
        if (movingUp) {
            if (!upQueue.isEmpty()) {
                int nextFloor = currentFloor + 1;
                if(upQueue.peek() == nextFloor){
                    log.info("Stop at floor " + nextFloor);
                    upQueue.poll();
                }
                return nextFloor;
            } else {
                movingUp = false;
                return getNextFloor(currentFloor);
            }
        } else {
            if (!downQueue.isEmpty()) {
                if(downQueue.peek()>currentFloor){
                    upQueue.offer(downQueue.peek());
                    movingUp = true;
                    return getNextFloor(currentFloor);
                }
                int nextFloor = currentFloor - 1;
                if(downQueue.peek() == nextFloor){
                    log.info("Stop at floor " + nextFloor);
                    downQueue.poll();
                }
                return nextFloor;

            } else {
                movingUp = true;
                return getNextFloor(currentFloor);
            }
        }
    }
}
