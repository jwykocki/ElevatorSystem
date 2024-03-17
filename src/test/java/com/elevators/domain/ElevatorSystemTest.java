package com.elevators.domain;

import com.elevators.domain.ElevatorSystem;
import com.elevators.domain.ElevatorSystemConfig;
import com.elevators.domain.exceptions.IncorrectFloorNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ElevatorSystemTest {


    private ElevatorSystem elevatorSystem;
    private ElevatorSystemConfig elevatorSystemConfig;



    @BeforeEach
    public void setUp() {
        elevatorSystemConfig = new ElevatorSystemConfig(3, 0, 5);

        elevatorSystem = new ElevatorSystem(elevatorSystemConfig);
    }

    private void makeSteps(int stepsCount){
        for(int i=0; i<stepsCount; i++){
            elevatorSystem.step();
        }
    }

    @Test
    void should_choose_elevator_index_1_when_that_is_nearest() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        makeSteps(3);
        int firstElevatorFloor = elevatorSystem.status().get(0).getCurrentFloor();
        int secondElevatorFloor = elevatorSystem.status().get(1).getCurrentFloor();
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
        int firstElevatorFloor = elevatorSystem.status().get(0).getCurrentFloor();
        int secondElevatorFloor = elevatorSystem.status().get(1).getCurrentFloor();
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
        int previousFloor = elevatorSystem.status().get(0).getCurrentFloor();
        int previousNextFloor = elevatorSystem.status().get(0).getNextFloor();
        //when
        elevatorSystem.step();
        //then
        int currentFloor = elevatorSystem.status().get(0).getCurrentFloor();
        int currentNextFloor = elevatorSystem.status().get(0).getNextFloor();
        assertThat(previousFloor).isEqualTo(0);
        assertThat(previousNextFloor).isEqualTo(0);
        assertThat(currentFloor).isEqualTo(0);
        assertThat(currentNextFloor).isEqualTo(1);
    }

    @Test
    void should_update_one_elevator_after_two_steps() throws IncorrectFloorNumberException {
        //given
        elevatorSystem.pickup(5); //0
        int previousFloor = elevatorSystem.status().get(0).getCurrentFloor();
        int previousNextFloor = elevatorSystem.status().get(0).getNextFloor();
        //when
        elevatorSystem.step();
        elevatorSystem.step();
        //then
        int currentFloor = elevatorSystem.status().get(0).getCurrentFloor();
        int currentNextFloor = elevatorSystem.status().get(0).getNextFloor();
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