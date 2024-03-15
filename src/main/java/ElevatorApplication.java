import domain.Elevator;
import domain.ElevatorSystem;

public class ElevatorApplication {


    public static void main(String[] args) {
        ElevatorSystem elevatorSystem = new ElevatorSystem(1);
        elevatorSystem.pickup(4, -1); //z 4 ktos chce zjechac w dol
        elevatorSystem.step();
        elevatorSystem.printStatus();
        elevatorSystem.pickup(2, 1); //z 2 ktos chce jechac do gory
        elevatorSystem.step();
        elevatorSystem.printStatus();
        elevatorSystem.pickup(5, 1); //na 5
        elevatorSystem.step();
        elevatorSystem.printStatus();
        elevatorSystem.step();
        elevatorSystem.pickup(0, -1); //na 5
        elevatorSystem.printStatus();
        elevatorSystem.step();
        elevatorSystem.printStatus();

    }
}
