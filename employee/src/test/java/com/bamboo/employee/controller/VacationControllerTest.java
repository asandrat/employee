package com.bamboo.employee.controller;

import com.bamboo.employee.custom.exception.VacationNotFoundException;
import com.bamboo.employee.model.ServerResponse;
import com.bamboo.employee.model.VacationDTO;
import com.bamboo.employee.model.VacationStatusDTO;
import com.bamboo.employee.service.VacationService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VacationController.class)
class VacationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VacationService vacationService;

    private VacationDTO vacation;

    @BeforeEach
    void setUp() {
        vacation = new VacationDTO();
        vacation.setId(1);
        vacation.setDateFrom("11/02/2021");
        vacation.setDateTo("15/02/2021");
        vacation.setDuration(4);
        vacation.setVacationStatus("Submitted");
    }

    @Test
    public void getAllVacationsTest() throws Exception {

        List<VacationDTO> vacations = Collections.singletonList(vacation);

        when(vacationService.getVacations(anyInt())).thenReturn(vacations);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1/vacations"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(vacation.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("[0].dateFrom").value(vacation.getDateFrom()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateTo").value(vacation.getDateTo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].duration").value(vacation.getDuration()));
    }

    @Test
    public void getVacationByIdTest() throws Exception {

        when(vacationService.findVacation(anyInt(), anyInt())).thenReturn(vacation);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1/vacations/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vacation.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateFrom").value(vacation.getDateFrom()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTo").value(vacation.getDateTo()));
    }

    @Test
    public void saveVacationTest() throws Exception {

        when(vacationService.addVacation(anyInt(), anyString(), anyString(), anyString())).thenReturn(vacation);

        mockMvc.perform(MockMvcRequestBuilders.post("/employees/1/vacations")
                .content(new ObjectMapper().writeValueAsString(vacation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateFrom").value(vacation.getDateFrom()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateTo").value(vacation.getDateTo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.vacationStatus").value(vacation.getVacationStatus()));
    }

    @Test
    public void changeVacationStatusTest() throws Exception {

        VacationStatusDTO vacationStatusDTO = new VacationStatusDTO();
        vacationStatusDTO.setVacationId(1);
        vacationStatusDTO.setVacationStatus("Approved");

        ServerResponse serverResponse = new ServerResponse(
                "Vacation with id: 1 is successfully updated."
        );

        mockMvc.perform(MockMvcRequestBuilders.patch("/employees/1/vacations")
                .content(new ObjectMapper().writeValueAsString(vacationStatusDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(serverResponse.getMessage()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteVacationTest() throws Exception {
        ServerResponse serverResponse = new ServerResponse(
                "Vacation with id: 3 is successfully deleted"
        );

        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/2/vacations/3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(serverResponse.getMessage()));
    }

    @Test
    public void getVacationByIdWhenIdIsInvalidTest() throws Exception {

        String message = "Could not find vacation with provided id";

        when(vacationService.findVacation(anyInt(), anyInt()))
                .thenThrow(new VacationNotFoundException(message));

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/1/vacations/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message));
    }
}
