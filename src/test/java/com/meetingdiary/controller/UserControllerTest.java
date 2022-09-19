package com.meetingdiary.controller;

import com.meetingdiary.AbstractTest;
import com.meetingdiary.datatransferobject.UserDTO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.EntityNotFoundException;
import com.meetingdiary.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractTest {

    @Override
    protected void setUp() {
        super.setUp();
    }

    @MockBean
    private UserService userService;

    @Test
    public void givenUser_whenGetUser_thenReturnUser() throws Exception {
        UserDO userDO = new UserDO("test");

        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(userDO);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/user/").param("userId", String.valueOf(1L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("test")));
    }

    @Test
    public void notGivenUser_whenGetUser_thenThrowException() throws Exception {

        Mockito.when(userService.findById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/user/").param("userId", String.valueOf(2L)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(2L)));
    }

    @Test
    public void createUser_thenReturnSuccess() throws Exception {
        UserDTO userDTO = UserDTO.newBuilder()
                .setId(1L)
                .setUsername("test")
                .createUserDTO();

        UserDO userDO = new UserDO("test");
        userDO.setPassword("password");

        Mockito.when(userService.create(Mockito.any(UserDO.class))).thenReturn(userDO);

        String inputJson = super.mapToJson(userDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(inputJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("test")));
    }

    @Test
    public void loginUser_thenReturnSuccess() throws Exception {
        UserDTO userDTO = UserDTO.newBuilder()
                .setId(1L)
                .setUsername("test")
                .createUserDTO();

        UserDO userDO = new UserDO("test");
        userDO.setPassword("password");

        Mockito.when(userService.login(Mockito.any(UserDO.class))).thenReturn(userDO);

        String inputJson = super.mapToJson(userDTO);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/user/login/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(inputJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("test")));
    }
}
