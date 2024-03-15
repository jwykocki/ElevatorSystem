package domain;

import lombok.extern.log4j.Log4j2;

import java.util.PriorityQueue;
import java.util.Queue;

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

        public void addRequest(int floor, int direction) throws Exception {
            if(floor > maximumFloor || floor < minimumFloor){
                throw new Exception("Incorrect floor number");
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


}
