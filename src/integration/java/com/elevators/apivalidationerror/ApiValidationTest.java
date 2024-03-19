package com.elevators.apivalidationerror;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.elevators.BaseIntegrationTest;

class ApiValidationTest extends BaseIntegrationTest{

    @Test
    void should_return_404_NOT_FOUND_when_floor_number_is_bigger_than_max() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": 99
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isNotFound()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).isEqualTo("{\"message\":\"Incorrect floor number\",\"status\":\"NOT_FOUND\"}");
    }

    @Test
    void should_return_404_NOT_FOUND_when_floor_number_is_smaller_than_min() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": -99
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult mvcResult = perform.andExpect(status().isNotFound()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        assertThat(json).isEqualTo("{\"message\":\"Incorrect floor number\",\"status\":\"NOT_FOUND\"}");
    }

    @Test
   void should_return_400_BAD_REQUEST_when_it_is_incorrect_floor_number() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": "a"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        perform.andExpect(status().isBadRequest());
    }

    @Test
    void should_return_400_BAD_REQUEST_when_floor_number_is_empty() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "floor": ""
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult result = perform.andExpect(status().isBadRequest()).andReturn();
        String json = result.getResponse().getContentAsString();
        assertThat(json).isEqualTo("{\"message\":\"Incorrect pickup request format\",\"status\":\"BAD_REQUEST\"}");
    }

    @Test
    void should_return_400_BAD_REQUEST_when_there_is_no_floor_field_in_request_body() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {
                        "other": "field"
                        }
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult result = perform.andExpect(status().isBadRequest()).andReturn();
        String json = result.getResponse().getContentAsString();
        assertThat(json).isEqualTo("{\"message\":\"Incorrect pickup request format\",\"status\":\"BAD_REQUEST\"}");
    }

    @Test
    void should_return_400_BAD_REQUEST_when_request_body_is_empty() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .content("""
                        {}
                        """)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        MvcResult result = perform.andExpect(status().isBadRequest()).andReturn();
        String json = result.getResponse().getContentAsString();
        assertThat(json).isEqualTo("{\"message\":\"Incorrect pickup request format\",\"status\":\"BAD_REQUEST\"}");
    }

    @Test
    void should_return_400_BAD_REQUEST_when_request_has_no_content() throws Exception {
        // given & when
        ResultActions perform = mockMvc.perform(post("/elevators/pickup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );
        // then
        perform.andExpect(status().isBadRequest());
    }
}
