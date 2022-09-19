package com.meetingdiary.controller;

import com.meetingdiary.AbstractTest;
import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.datatransferobject.UserDTO;
import com.meetingdiary.datatransferobject.UserDTOForMeeting;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;
import com.meetingdiary.service.meeting.MeetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MeetingController.class)
public class MeetingControllerTest extends AbstractTest {

    @Override
    protected void setUp() {
        super.setUp();
    }

    @MockBean
    private MeetingService meetingService;

    @Test
    public void givenMeeting_whenGetMeeting_thenReturnMeeting() throws Exception {
        MeetingDO meetingDO = new MeetingDO(LocalDateTime.now(),
                new UserDO("test User"),
                "testPerson", 10.0, 20.0);

        Mockito.when(meetingService.findById(Mockito.anyLong())).thenReturn(meetingDO);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/").param("meetingId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitude", is(10.0)));
    }

    @Test
    public void notGivenMeeting_whenGetMeeting_thenThrowException() throws Exception {
        Mockito.when(meetingService.findById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/").param("meetingId", String.valueOf(2L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertThrows(EntityNotFoundException.class, () -> meetingService.findById(2L)));

    }

    @Test
    public void givenMeeting_whenGetUserMeetings_thenReturnMeetings() throws Exception {
        MeetingDO meetingDO = new MeetingDO(LocalDateTime.now(),
                new UserDO("test User"),
                "testPerson", 10.0, 20.0);

        Mockito.when(meetingService.findUserMeetings(Mockito.anyLong())).thenReturn(List.of(meetingDO));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/user/").param("userId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].latitude", is(10.0)));
    }

    @Test
    public void getUserMeetings_thenThrowException() throws Exception {
        Mockito.when(meetingService.findUserMeetings(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/user/").param("userId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertThrows(EntityNotFoundException.class, () -> meetingService.findUserMeetings(1L)));
    }

    @Test
    public void createMeeting_thenReturnSuccess() throws Exception {
        MeetingDTO meetingDTO = MeetingDTO.newBuilder()
                .setMeetingDate(LocalDateTime.now())
                .setUserDTO(new UserDTOForMeeting("test User"))
                .setLatitude(10.0)
                .setLongitude(20.0)
                .createMeetingDTO();

        String inputJson = super.mapToJson(List.of(meetingDTO));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/meeting/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(inputJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    public void createMeetingWithEmptyList_thenThrowException() throws Exception {

        String inputJson = super.mapToJson(new ArrayList<>());

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/meeting/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(inputJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUserMeetingsBetweenDates_success() throws Exception {
        MeetingDO meetingDO = new MeetingDO(LocalDateTime.now(),
                new UserDO("test User"),
                "testPerson", 10.0, 20.0);

        Mockito.when(
                meetingService.findMeetingBetweenDates(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.anyLong()))
                .thenReturn(List.of(meetingDO));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/dates/")
                                .param("meetingDateStart", String.valueOf(LocalDateTime.now()))
                                .param("meetingDateEnd", String.valueOf(LocalDateTime.MAX))
                                .param("userId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].latitude", is(10.0)));
    }

    @Test
    public void getUserMeetingsBetweenDates_throwException() throws Exception {

        Mockito.when(meetingService.findMeetingBetweenDates(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/dates/")
                                .param("meetingDateStart", String.valueOf(LocalDateTime.now()))
                                .param("meetingDateEnd", String.valueOf(LocalDateTime.MAX))
                                .param("userId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertThrows(EntityNotFoundException.class,
                        () -> meetingService.findMeetingBetweenDates(LocalDateTime.now(), LocalDateTime.MAX, 1L)));
    }

    @Test
    public void getUserMeetingsBetweenDates_throwExceptionMissingParam() throws Exception {
        //test when a required parameter is not sent to the endpoint

        Mockito.when(meetingService.findMeetingBetweenDates(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.anyLong()))
                .thenThrow(MethodArgumentTypeMismatchException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/meeting/dates/")
                                .param("meetingDateStart", String.valueOf(LocalDateTime.now()))
                                .param("userId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertThrows(MethodArgumentTypeMismatchException.class,
                        () -> meetingService.findMeetingBetweenDates(LocalDateTime.now(), LocalDateTime.MAX, 1L)));
    }

}
