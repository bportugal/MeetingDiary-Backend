package com.meetingdiary.controller;

import com.meetingdiary.controller.mapper.MeetingMapper;
import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;
import com.meetingdiary.service.meeting.MeetingService;
import com.meetingdiary.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Operations related to meetings will be routed by this controller.
 * <p/>
 */
@CrossOrigin
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(final MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @Operation(summary = "Get Meeting by its Id")
    @GetMapping("/")
    public MeetingDTO getMeeting(@RequestParam Long meetingId) throws EntityNotFoundException {
        return MeetingMapper.makeMeetingDTO(meetingService.findById(meetingId));
    }

    @Operation(summary = "Get User's meetings")
    @GetMapping("/user")
    public List<MeetingDTO> getMeetingsOfUser(@RequestParam Long userId) throws EntityNotFoundException {
        return MeetingMapper.makeMeetingDTOList(meetingService.findUserMeetings(userId));
    }

    @Operation(summary = "Add new Meeting")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMeeting(@Valid @RequestBody List<MeetingDTO> meetingDTO) throws ConstraintsViolationException, EntityNotFoundException {
        List<MeetingDO> meetingDO = MeetingMapper.makeMeetingDOList(meetingDTO);
        if(meetingDO.isEmpty()) {
            throw new ConstraintsViolationException("No meeting was sent");
        }
        meetingService.create(meetingDO);
    }

    @Operation(summary = "Get User's meetings between 2 dates")
    @GetMapping("/dates")
    public List<MeetingDTO> getMeetings(@RequestParam
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        LocalDateTime meetingDateStart,
                                        @RequestParam
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                        LocalDateTime meetingDateEnd,
                                        @RequestParam Long userId) throws EntityNotFoundException {
        return MeetingMapper.makeMeetingDTOList(meetingService.findMeetingBetweenDates(meetingDateStart, meetingDateEnd, userId));
    }
}
