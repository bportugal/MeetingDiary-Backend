package com.meetingdiary.service.meeting;

import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {

    MeetingDO findById(Long meetingId) throws EntityNotFoundException;

    void create(MeetingDO meetingDO) throws ConstraintsViolationException;

    List<MeetingDO> findMeetingBetweenDates(LocalDateTime meetingDateStart, LocalDateTime meetingDateEnd, Long id);
}
