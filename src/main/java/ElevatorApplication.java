import domain.Elevator;
import domain.ElevatorSystem;

public class ElevatorApplication {


    public static void main(String[] args) throws Exception {
        ElevatorSystem s = new ElevatorSystem(1, 0, 5);
        s.pickup(0);
        s.printStatus();
        s.step();
        s.printStatus();
        s.step();
        s.printStatus();
        s.step();
        s.printStatus();
        s.step();
        s.pickup(1);
        s.printStatus();
        s.step();
        s.printStatus();
        s.step();
        s.printStatus();
        s.step();
        s.printStatus();
        s.step();
    }
}
