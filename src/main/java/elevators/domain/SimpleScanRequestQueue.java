package elevators.domain;

import elevators.domain.exceptions.IncorrectFloorNumberException;
import lombok.extern.log4j.Log4j2;

import java.util.*;

@Log4j2
public class SimpleScanRequestQueue implements FloorRequestQueue {
        private final Queue<Integer> upQueue;
        private final Queue<Integer> downQueue;
        private boolean movingUp;
        private final int maximumFloor;
        private final int minimumFloor;

        public SimpleScanRequestQueue(int minimumFloor, int maximumFloor) {
            this.upQueue = new PriorityQueue<>();
            this.downQueue = new PriorityQueue<>((a, b) -> b - a);
            this.movingUp = true;
            this.maximumFloor = maximumFloor;
            this.minimumFloor = minimumFloor;
        }

        public void addRequest(int floor, int direction){
            if(floor > maximumFloor || floor < minimumFloor){
                throw new IncorrectFloorNumberException();
            }
            if (direction > 0) {
                upQueue.offer(floor);
            } else {
                downQueue.offer(floor);
            }
        }

        public int getNextFloor(int currentFloor) {
            if(upQueue.isEmpty() && downQueue.isEmpty()){
                return currentFloor;
            }

            if(movingUp){
                if(!upQueue.isEmpty()) {
                    if (upQueue.peek() == currentFloor) {
                        log.info("Stop at floor " + currentFloor);
                        upQueue.poll();
                    }else{
                        currentFloor ++;
                    }
                    return currentFloor;
                }else{
                    movingUp = false;
                    return getNextFloor(currentFloor);
                }
            }
            else{
                if(!downQueue.isEmpty()) {
                    if (downQueue.peek() == currentFloor) {
                        log.info("Stop at floor " + currentFloor);
                        downQueue.poll();
                    }else{
                        currentFloor --;
                    }
                    return currentFloor;
                }else{
                    movingUp = true;
                    return getNextFloor(currentFloor);
                }
            }
    }

    public int getDistance(int currentFloor, int destinationFloor){
        if(movingUp){
            if(currentFloor <= destinationFloor){
                return destinationFloor - currentFloor;
            }else {
                int last = upQueue.stream().max(Integer::compareTo).orElse(0);
                return last - currentFloor + last - destinationFloor;
            }
        }
        else{
            if(currentFloor >= destinationFloor){
                return currentFloor - destinationFloor;
            }else {
                int last = downQueue.stream().min(Integer::compareTo).orElseThrow();
                return currentFloor - last + destinationFloor - last;
            }
        }

    }


}
