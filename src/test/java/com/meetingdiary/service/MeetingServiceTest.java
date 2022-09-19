package com.meetingdiary.service;

import com.meetingdiary.AbstractTest;
import com.meetingdiary.dataaccessobject.MeetingRepository;
import com.meetingdiary.dataaccessobject.UserRepository;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;
import com.meetingdiary.service.meeting.DefaultMeetingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@WebMvcTest(DefaultMeetingService.class)
public class MeetingServiceTest extends AbstractTest {

    @Override
    protected void setUp() {
        super.setUp();
    }

    private MeetingDO meeting;

    private UserDO user;

    @MockBean
    private MeetingRepository meetingRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private DefaultMeetingService meetingService;

    @BeforeEach
    public void config() throws EntityNotFoundException {

        user = new UserDO("testUser@test.com");

        meeting = new MeetingDO(LocalDateTime.now(), user, "person",10.0, 20.0);

        Mockito.when(meetingRepository.findById(1L)).thenReturn(Optional.of(meeting));
    }

    @Test
    public void findMeeting_success() throws EntityNotFoundException {
        MeetingDO meetingDO = meetingService.findById(1L);
        Assertions.assertEquals(meetingDO.getPersonName(), meeting.getPersonName());
    }

    @Test
    public void findMeeting_exception() {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    meetingService.findById(2L);
                });

        Assertions.assertEquals("Could not find entity with id: 2", exception.getMessage());
    }

    @Test
    public void findUserMeeting_success() throws EntityNotFoundException {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(meetingRepository.findByUserId(1L)).thenReturn(List.of(meeting));
        List<MeetingDO> meetingList = meetingService.findUserMeetings(1L);
        Assertions.assertEquals(1, meetingList.size());
    }

    @Test
    public void findUserMeeting_exception() {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    meetingService.findUserMeetings(3L);
                });

        Assertions.assertEquals("User doesn't exist.", exception.getMessage());
    }

    @Test
    public void findMeetingBetweenDates_success() throws EntityNotFoundException {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(meetingRepository.findAllByMeetingDateBetweenAndUserId(
                        LocalDateTime.of(2022, Month.SEPTEMBER, 11, 0,0,0),
                        LocalDateTime.of(2022, Month.SEPTEMBER, 30, 0,0,0),
                        1L))
                .thenReturn(List.of(meeting));

        List<MeetingDO> meetingList = meetingService.findMeetingBetweenDates(
                LocalDateTime.of(2022, Month.SEPTEMBER, 11, 0,0,0),
                LocalDateTime.of(2022, Month.SEPTEMBER, 30, 0,0,0),
                1L);
        Assertions.assertEquals(1, meetingList.size());
    }

    @Test
    public void findMeetingBetweenDates_throwException() {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    meetingService.findMeetingBetweenDates(
                            LocalDateTime.of(2022, Month.SEPTEMBER, 11, 0,0,0),
                            LocalDateTime.of(2022, Month.SEPTEMBER, 30, 0,0,0),
                            1L);
                });

        Assertions.assertEquals("User doesn't exist.", exception.getMessage());
    }

    @Test
    public void createMeeting_success() throws ConstraintsViolationException, EntityNotFoundException {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(meetingRepository.saveAll(List.of(meeting))).thenReturn(List.of(meeting));

        meetingService.create(List.of(meeting));

    }

    @Test
    public void createMeeting_MeetingAlreadyExistsException() throws ConstraintsViolationException, EntityNotFoundException {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        Mockito.when(meetingRepository.saveAll(List.of(meeting))).thenThrow(DataIntegrityViolationException.class);

        ConstraintsViolationException exception = Assertions.assertThrows(
                ConstraintsViolationException.class,
                () -> {
                    meetingService.create(List.of(meeting));
                });

        Assertions.assertEquals("Meeting already exists", exception.getMessage());
    }

    @Test
    public void createMeeting_throwException() {
        EntityNotFoundException exception = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> {
                    meetingService.create(List.of(meeting));
                });

        Assertions.assertEquals("User does not exist!", exception.getMessage());
    }

    @Test
    public void createMeeting_NoMeetingDate_throwException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        meeting.setMeetingDate(null);
        ConstraintsViolationException exception = Assertions.assertThrows(
                ConstraintsViolationException.class,
                () -> {
                    meetingService.create(List.of(meeting));
                });

        Assertions.assertEquals("Can not create meeting without date", exception.getMessage());
    }

    @Test
    public void createMeeting_NoPersonName_throwException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        meeting.setPersonName(null);
        ConstraintsViolationException exception = Assertions.assertThrows(
                ConstraintsViolationException.class,
                () -> {
                    meetingService.create(List.of(meeting));
                });

        Assertions.assertEquals("Can not create meeting without person", exception.getMessage());
    }

    @Test
    public void createMeeting_NoLatitude_throwException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        meeting.setLatitude(null);
        ConstraintsViolationException exception = Assertions.assertThrows(
                ConstraintsViolationException.class,
                () -> {
                    meetingService.create(List.of(meeting));
                });

        Assertions.assertEquals("Can not create meeting without latitude of the meeting", exception.getMessage());
    }

    @Test
    public void createMeeting_NoLongitude_throwException() {
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        meeting.setLongitude(null);
        ConstraintsViolationException exception = Assertions.assertThrows(
                ConstraintsViolationException.class,
                () -> {
                    meetingService.create(List.of(meeting));
                });

        Assertions.assertEquals("Can not create meeting without longitude of the meeting", exception.getMessage());
    }
}
