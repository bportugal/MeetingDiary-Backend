package com.meetingdiary.service;

import com.meetingdiary.AbstractTest;
import com.meetingdiary.dataaccessobject.UserRepository;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EmailInvalidException;
import com.meetingdiary.exception.EntityNotFoundException;
import com.meetingdiary.service.user.DefaultUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@WebMvcTest(DefaultUserService.class)
public class UserServiceTest extends AbstractTest {
    @Override
    protected void setUp() {
        super.setUp();
    }

    private UserDO user;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private DefaultUserService userService;

    @BeforeEach
    public void config() throws EntityNotFoundException {
        user = new UserDO("Test@test.com");

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    public void findUser_success() throws EntityNotFoundException {
        UserDO userDO = userService.findById(1L);
        Assertions.assertEquals(userDO.getUsername(), user.getUsername());
    }

    @Test
    public void findUser_exception() throws EntityNotFoundException {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    userService.findById(2L);
                });

        Assertions.assertEquals("Could not find entity with id: 2", exception.getMessage());
    }

    @Test
    public void findUserByName_success() throws EntityNotFoundException {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        UserDO userDO = userService.findByUsername("Test@test.com");
        Assertions.assertEquals(userDO.getUsername(), user.getUsername());
    }

    @Test
    public void findUserByName_exception() throws EntityNotFoundException {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    userService.findByUsername("Wrong Name");
                });

        Assertions.assertEquals("User does not exist!", exception.getMessage());
    }

    @Test
    public void createUser_success() throws Exception {
        UserDO userDO = new UserDO("testCreate@test.com");
        userDO.setPassword("testPass");
        Mockito.when(userRepository.save(Mockito.any(UserDO.class))).thenReturn(user);
        Assertions.assertEquals(userService.create(userDO).getUsername(), user.getUsername());
    }

    @Test
    public void createUser_emailOrPasswordException() throws Exception {
        DataIntegrityViolationException exception = Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    userService.create(user);
                });

        Assertions.assertEquals("Email and/or Passoword are missing", exception.getMessage());
    }

    @Test
    public void createUser_emailMalformatedException() throws Exception {
        UserDO userDO = new UserDO("testCreate");
        userDO.setPassword("testPass");

        EmailInvalidException exception = Assertions.assertThrows(
                EmailInvalidException.class,
                () -> {
                    userService.create(userDO);
                });

        Assertions.assertEquals("Email has an invalid format.", exception.getMessage());
    }

    @Test
    public void createUser_emailAlreadyBeingUsed_Exception() throws Exception {
        UserDO userDO = new UserDO("testCreate@test.com");
        userDO.setPassword("testPass");
        Mockito.when(userRepository.save(Mockito.any(UserDO.class))).thenThrow(DataIntegrityViolationException.class);

        ConstraintsViolationException exception = Assertions.assertThrows(
                ConstraintsViolationException.class,
                () -> {
                    userService.create(userDO);
                });

        Assertions.assertEquals("Email already being used.", exception.getMessage());
    }

    @Test
    public void loginUser_success() throws Exception {
        UserDO userDO = new UserDO("testCreate@test.com");
        userDO.setPassword("testPass");
        user.setPassword("testPass");
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Assertions.assertEquals(userService.login(userDO).getUsername(), user.getUsername());
    }

    @Test
    public void loginUser_emailOrPasswordMissingException() {
        DataIntegrityViolationException exception = Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    userService.login(user);
                });

        Assertions.assertEquals("Email and/or Passoword are missing", exception.getMessage());
    }

    @Test
    public void loginUser_PasswordWrongException() {
        UserDO userDO = new UserDO("testCreate@test.com");
        userDO.setPassword("testPass");
        user.setPassword("passRight");
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);

        DataIntegrityViolationException exception = Assertions.assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    userService.login(userDO);
                });

        Assertions.assertEquals("Email and/or Passoword are wrong", exception.getMessage());
    }

    @Test
    public void loginUser_emailMalformatedException() throws Exception {
        UserDO userDO = new UserDO("testCreate");
        userDO.setPassword("testPass");

        EmailInvalidException exception = Assertions.assertThrows(
                EmailInvalidException.class,
                () -> {
                    userService.login(userDO);
                });

        Assertions.assertEquals("Email has an invalid format.", exception.getMessage());
    }

    @Test
    public void loginUser_emailAlreadyBeingUsed_Exception() throws Exception {
        UserDO userDO = new UserDO("testCreate@test.com");
        userDO.setPassword("testPass");
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);

        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    userService.login(userDO);
                });

        Assertions.assertEquals("User does not exist!", exception.getMessage());
    }
}
