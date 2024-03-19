package com.elevators.features;

import com.elevators.BaseIntegrationTest;
import com.elevators.domain.dto.ElevatorDto;
import com.elevators.domain.dto.PickupResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;
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

        //step 2: user wants to pick up an elevator from the floor 3
        // given & when
        ResultActions performFirstPickup = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": 3
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultFirstPickup = performFirstPickup.andExpect(status().isOk()).andReturn();
        String jsonFirstPickup = mvcResultFirstPickup.getResponse().getContentAsString();
        PickupResponseDto responseFirstPickup = objectMapper.readValue(jsonFirstPickup, PickupResponseDto.class);
        assertThat(responseFirstPickup.elevatorId()).isEqualTo(0);

        //step 3: user make first step request
        // given & when
        ResultActions performFirstStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performFirstStep.andExpect(status().isOk());

        //step 4: user make check status and see that elevator is going up
        // given & when
        ResultActions getSecondStatus = mockMvc.perform(get("/elevators/status")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResultSecondStatus = getSecondStatus.andExpect(status().isOk()).andReturn();
        String jsonSecondStatus = mvcResultSecondStatus.getResponse().getContentAsString();
        List<ElevatorDto> responseSecondStatus = objectMapper.readValue(jsonSecondStatus, new TypeReference<>() {});
        assertThat(responseSecondStatus.get(0).getNextFloor()).isEqualTo(1);

        //step 5: user make two more step requests
        // given & when
        ResultActions performSecondStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performSecondStep.andExpect(status().isOk());
        // given & when
        ResultActions performThirdStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performThirdStep.andExpect(status().isOk());

        //step 6: user make check status and see that elevator 0 is on the floor 2
        // given & when
        ResultActions getThirdStatus = mockMvc.perform(get("/elevators/status")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResultThirdStatus = getThirdStatus.andExpect(status().isOk()).andReturn();
        String jsonThirdStatus = mvcResultThirdStatus.getResponse().getContentAsString();
        List<ElevatorDto> responseThirdStatus = objectMapper.readValue(jsonThirdStatus, new TypeReference<>() {});
        assertThat(responseThirdStatus.get(0).getCurrentFloor()).isEqualTo(2);

        //step 7: user wants to pick up an elevator from the floor 1 and expect in will be elevator 1
        // given & when
        ResultActions performSecondPickup = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": 1
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultSecondPickup = performSecondPickup.andExpect(status().isOk()).andReturn();
        String jsonSecondPickup = mvcResultSecondPickup.getResponse().getContentAsString();
        PickupResponseDto responseSecondPickup = objectMapper.readValue(jsonSecondPickup, PickupResponseDto.class);
        assertThat(responseSecondPickup.elevatorId()).isEqualTo(1);

        //step 8: user make two more step requests
        // given & when
        ResultActions performFourthStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performFourthStep.andExpect(status().isOk());
        // given & when
        ResultActions performFifthStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performFifthStep .andExpect(status().isOk());

        //step 9: user make check status and see that elevator 0 is on the floor 3, elevator 1 on 1
        // given & when
        ResultActions getFourthStatus = mockMvc.perform(get("/elevators/status")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResultFourthStatus = getFourthStatus.andExpect(status().isOk()).andReturn();
        String jsonFourthStatus = mvcResultFourthStatus.getResponse().getContentAsString();
        List<ElevatorDto> responseFourthStatus = objectMapper.readValue(jsonFourthStatus, new TypeReference<>() {});
        assertThat(responseFourthStatus.get(0).getCurrentFloor()).isEqualTo(3);
        assertThat(responseFourthStatus.get(1).getCurrentFloor()).isEqualTo(1);

        //step 10: user wants to pick up an elevator from the floor 2 and expect in will be elevator 1
        // given & when
        ResultActions performThirdPickup = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": 2
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultThirdPickup = performThirdPickup.andExpect(status().isOk()).andReturn();
        String jsonThirdPickup = mvcResultThirdPickup.getResponse().getContentAsString();
        PickupResponseDto responseThirdPickup= objectMapper.readValue(jsonThirdPickup, PickupResponseDto.class);
        assertThat(responseThirdPickup.elevatorId()).isEqualTo(1);

        //step 11: user wants to pick up an elevator from the floor 0 and expect in will be elevator 2
        // given & when
        ResultActions performFourthPickup = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": 0
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResultFourthPickup = performFourthPickup.andExpect(status().isOk()).andReturn();
        String jsonFourthPickup = mvcResultFourthPickup.getResponse().getContentAsString();
        PickupResponseDto responseFourthPickup = objectMapper.readValue(jsonFourthPickup, PickupResponseDto.class);
        assertThat(responseFourthPickup.elevatorId()).isEqualTo(2);

        //step 12: user make two more step requests
        // given & when
        ResultActions performSixthStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performSixthStep.andExpect(status().isOk());
        // given & when
        ResultActions performSeventhStep = mockMvc.perform(post("/elevators/step")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        performSeventhStep.andExpect(status().isOk());

        //step 13: user make final status check
        // given & when
        ResultActions getFifthStatus = mockMvc.perform(get("/elevators/status")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        // then
        MvcResult mvcResultFifthStatus = getFifthStatus.andExpect(status().isOk()).andReturn();
        String jsonFifthStatus = mvcResultFifthStatus.getResponse().getContentAsString();

        assertThat(jsonFifthStatus).isEqualToIgnoringWhitespace("""
                [
                     {
                         "id": 0,
                         "currentFloor": 3,
                         "nextFloor": 3
                     },
                     {
                         "id": 1,
                         "currentFloor": 2,
                         "nextFloor": 2
                     },
                     {
                         "id": 2,
                         "currentFloor": 0,
                         "nextFloor": 0
                     }
                 ]
                """);
    }


}
