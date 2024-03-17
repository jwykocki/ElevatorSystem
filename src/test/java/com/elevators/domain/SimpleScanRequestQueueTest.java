package com.elevators.domain;

import com.elevators.domain.SimpleScanRequestQueue;
import com.elevators.domain.exceptions.IncorrectFloorNumberException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.Assert.assertEquals;

class SimpleScanRequestQueueTest {

    private SimpleScanRequestQueue requestQueue;
    private static final int MAX_FLOOR = 5;
    private static final int MIN_FLOOR = 0;


    @BeforeEach
    public void setUp() {
        requestQueue = new SimpleScanRequestQueue(MIN_FLOOR, MAX_FLOOR);
    }




    @Test
    void should_next_floor_be_3_when_first_request_was_3() throws Exception {
        //given && when
        requestQueue.addRequest(3, 1);
        requestQueue.addRequest(5, 1);

        //then
        assertThat(requestQueue.getNextFloor(2)).isEqualTo(3);
    }

    @Test
    public void should_next_floors_be_3_and_5_when_elevator_is_moving_up() throws Exception {
        //given && when
        requestQueue.addRequest(3, 1);
        requestQueue.addRequest(5, 1);
        //then
        assertThat(requestQueue.getNextFloor(3)).isEqualTo(3);
        assertThat(requestQueue.getNextFloor(4)).isEqualTo(5);
    }

    @Test
    public void should_next_floors_be_3_and_0_when_elevator_is_moving_up_and_down() throws Exception {
        //given && when
        requestQueue.addRequest(3, 1);
        requestQueue.addRequest(0, -1);
        //then
        assertThat(requestQueue.getNextFloor(2)).isEqualTo(3);
        assertThat(requestQueue.getNextFloor(3)).isEqualTo(3);
        assertThat(requestQueue.getNextFloor(1)).isEqualTo(0);
    }

    @Test
    public void should_next_floor_be_current_floor_when_queues_are_empty() {
        //given && when && then
        assertThat(requestQueue.getNextFloor(2)).isEqualTo(2);
    }

    @Test
    public void should_throw_an_exception_when_floor_is_bigger_than_max_floor() {
        //given
        int floor = MAX_FLOOR + 1;
        //when
        Throwable thrown = catchThrowable(() ->  requestQueue.addRequest(floor, 1));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(IncorrectFloorNumberException.class)
                .hasMessage("Incorrect floor number");
    }

    @Test
    public void should_throw_an_exception_when_floor_is_smaller_than_min_floor() {
        //given
        int floor = MIN_FLOOR - 1;
        //when
        Throwable thrown = catchThrowable(() ->  requestQueue.addRequest(floor, -1));
        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(IncorrectFloorNumberException.class)
                .hasMessage("Incorrect floor number");
    }




}