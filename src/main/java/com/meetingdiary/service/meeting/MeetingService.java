package com.meetingdiary.service.meeting;

import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;

public interface MeetingService {

    MeetingDO findById(Long meetingId) throws EntityNotFoundException;

    void create(MeetingDO meetingDO) throws ConstraintsViolationException;
}
