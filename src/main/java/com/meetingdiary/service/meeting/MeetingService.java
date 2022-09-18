package com.meetingdiary.service.meeting;

import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MeetingService {

    MeetingDO findById(Long meetingId) throws EntityNotFoundException;

    void create(List<MeetingDO> meetingDO) throws ConstraintsViolationException, EntityNotFoundException;

    List<MeetingDO> findMeetingBetweenDates(LocalDateTime meetingDateStart, LocalDateTime meetingDateEnd, Long id) throws EntityNotFoundException;

    List<MeetingDO> findUserMeetings(Long userId) throws EntityNotFoundException;
}
