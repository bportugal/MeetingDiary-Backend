package com.meetingdiary.service.user;

import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;

import java.util.List;

public interface UserService {

    UserDO findById(Long userId) throws EntityNotFoundException;

    UserDO findByUsername(String username) throws EntityNotFoundException;

    List<MeetingDO> findMeetings(Long userId) throws EntityNotFoundException;

    void create(UserDO carDO) throws ConstraintsViolationException;
}
