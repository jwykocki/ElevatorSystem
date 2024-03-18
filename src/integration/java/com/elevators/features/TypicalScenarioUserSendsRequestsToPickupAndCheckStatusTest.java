package com.elevators.features;

import com.elevators.BaseIntegrationTest;
import com.elevators.domain.dto.PickupRequestDto;
import com.elevators.domain.dto.PickupResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TypicalScenarioUserSendsRequestsToPickupAndCheckStatusTest extends BaseIntegrationTest {



    @Test
    public void user_use_system_with_3_elevators_and_wants_to_pickup_elevators_and_checks_their_statuses() throws Exception {

        //step 1: user checks that every elevator is on the floor zero
        // given & when
        ResultActions getFirstStatus = mockMvc.perform(get("/elevators/status")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResultFirstStatus = getFirstStatus.andExpect(status().isOk()).andReturn();
        String jsonFirstStatus = mvcResultFirstStatus.getResponse().getContentAsString();
        assertThat(jsonFirstStatus).isEqualToIgnoringWhitespace("""
                [
                     {
                         "id": 0,
                         "currentFloor": 0,
                         "nextFloor": 0
                     },
                     {
                         "id": 1,
                         "currentFloor": 0,
                         "nextFloor": 0
                     },
                     {
                         "id": 2,
                         "currentFloor": 0,
                         "nextFloor": 0
                     }
                 ]
                """);

        //step 2: user wants to pickup an elevator from the floor 3
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": 3
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        PickupResponseDto response = new ObjectMapper().readValue(json, PickupResponseDto.class);
        assertThat(response.elevatorId()).isEqualTo(0);
    }


}
