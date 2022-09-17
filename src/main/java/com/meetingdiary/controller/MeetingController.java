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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Operations related to meetings will be routed by this controller.
 * <p/>
 */
@CrossOrigin
@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    private final UserService userService;

    @Autowired
    public MeetingController(final MeetingService meetingService, final UserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }

    @Operation(summary = "Get Meeting by Id")
    @GetMapping("/")
    public MeetingDTO getMeeting(@RequestParam Long meetingId) throws EntityNotFoundException {
        return MeetingMapper.makeMeetingDTO(meetingService.findById(meetingId));
    }

    @Operation(summary = "Add new Meeting")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMeeting(@Valid @RequestBody MeetingDTO meetingDTO) throws ConstraintsViolationException {
        MeetingDO meetingDO = MeetingMapper.makeMeetingDO(meetingDTO);
        meetingService.create(meetingDO);
    }
}
