package com.meetingdiary.controller;

import com.meetingdiary.AbstractTest;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserController.class)
public class UserControllerTest extends AbstractTest {

    @Override
    protected void setUp()
    {
        super.setUp();
    }

    @MockBean
    private UserService userService;

    @Test
    public void givenUser_whenGetUser_thenReturnUser() throws Exception {
        UserDO userDO = new UserDO("test");

        Mockito.when(userService.findById(Mockito.anyLong())).thenReturn(userDO);


    }
}
