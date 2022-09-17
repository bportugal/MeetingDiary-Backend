package com.meetingdiary.controller;

import com.meetingdiary.controller.mapper.MeetingMapper;
import com.meetingdiary.controller.mapper.UserMapper;
import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.datatransferobject.UserDTO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;
import com.meetingdiary.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Operations related to users will be routed by this controller.
 * <p/>
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get User by Id")
    @GetMapping("/")
    public UserDTO getUser(@RequestParam Long userId) throws EntityNotFoundException {
        return UserMapper.makeUserDTO(userService.findById(userId));
    }

    @Operation(summary = "Get User's meetings")
    @GetMapping("/meetings")
    public List<MeetingDTO> getMeetings(@RequestParam Long userId) throws EntityNotFoundException {
        return MeetingMapper.makeMeetingDTOList(userService.findMeetings(userId));
    }

    @Operation(summary = "Add new User")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserDTO userDTO) throws ConstraintsViolationException {
        UserDO userDO = UserMapper.makeUserDO(userDTO);
        userService.create(userDO);
    }
}
