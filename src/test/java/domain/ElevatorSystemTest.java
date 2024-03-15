package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorSystemTest {

    private ElevatorSystem elevatorSystem;
    private static final int MAX_FLOOR = 5;
    private static final int MIN_FLOOR = 0;
    private static final int ELEVATORS_NUMBER = 3;

    private void makeSteps(int stepsCount){
        for(int i=0; i<stepsCount; i++){
            elevatorSystem.step();
        }
    }

    @BeforeEach
    public void setUp() {
        elevatorSystem = new ElevatorSystem(ELEVATORS_NUMBER, MIN_FLOOR, MAX_FLOOR);
    }

    @Test
    void should_choose_elevator_index_1_when_that_is_nearest() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        makeSteps(3);
        int firstElevatorFloor = elevatorSystem.status().get(0).get(1);
        int secondElevatorFloor = elevatorSystem.status().get(1).get(1);
        //when
        int chosenIx = elevatorSystem.pickup(1); //1
        //then
        assertThat(firstElevatorFloor).isEqualTo(2);
        assertThat(secondElevatorFloor).isEqualTo(0);
        assertThat(chosenIx).isEqualTo(1);
    }

    @Test
    void should_choose_elevator_index_0_when_that_is_nearest() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(3); //0
        makeSteps(3);
        int firstElevatorFloor = elevatorSystem.status().get(0).get(1);
        int secondElevatorFloor = elevatorSystem.status().get(1).get(1);
        //when
        int chosenIx = elevatorSystem.pickup(5); //1
        //then
        assertThat(firstElevatorFloor).isEqualTo(2);
        assertThat(secondElevatorFloor).isEqualTo(0);
        assertThat(chosenIx).isEqualTo(0);
    }

    @Test
    void should_return_correct_status() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        makeSteps(3);
        elevatorSystem.pickup(1); //1
        makeSteps(2);
        //when
        String status = elevatorSystem.status().toString();
        //then
        assertThat(status).isEqualTo("[[0, 4, 5], [1, 1, 1], [2, 0, 0]]");
    }

    @Test
    void should_update_one_elevator_after_one_step() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        int previousFloor = elevatorSystem.status().get(0).get(1);
        int previousNextFloor = elevatorSystem.status().get(0).get(2);
        //when
        elevatorSystem.step();
        //then
        int currentFloor = elevatorSystem.status().get(0).get(1);
        int currentNextFloor = elevatorSystem.status().get(0).get(2);
        assertThat(previousFloor).isEqualTo(0);
        assertThat(previousNextFloor).isEqualTo(0);
        assertThat(currentFloor).isEqualTo(0);
        assertThat(currentNextFloor).isEqualTo(1);
    }

    @Test
    void should_update_one_elevator_after_two_steps() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        int previousFloor = elevatorSystem.status().get(0).get(1);
        int previousNextFloor = elevatorSystem.status().get(0).get(2);
        //when
        elevatorSystem.step();
        elevatorSystem.step();
        //then
        int currentFloor = elevatorSystem.status().get(0).get(1);
        int currentNextFloor = elevatorSystem.status().get(0).get(2);
        assertThat(previousFloor).isEqualTo(0);
        assertThat(previousNextFloor).isEqualTo(0);
        assertThat(currentFloor).isEqualTo(1);
        assertThat(currentNextFloor).isEqualTo(2);
    }

    @Test
    void should_update_every_elevator_after_steps() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        makeSteps(4);
        elevatorSystem.pickup(2); //1
        makeSteps(1);
        String previousStatus = elevatorSystem.status().toString();
        //when
        makeSteps(1);
        String currentStatus = elevatorSystem.status().toString();
        //then
        assertThat(previousStatus).isEqualTo("[[0, 4, 5], [1, 0, 1], [2, 0, 0]]");
        assertThat(currentStatus).isEqualTo("[[0, 5, 5], [1, 1, 2], [2, 0, 0]]");
    }
}