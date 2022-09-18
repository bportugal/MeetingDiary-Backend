package com.meetingdiary.dataaccessobject;

import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<MeetingDO, Long> {
    List<MeetingDO> findByUserId(Long userId) throws EntityNotFoundException;

    List<MeetingDO> findAllByMeetingDateBetweenAndUserId(LocalDateTime meetingDateStart, LocalDateTime meetingDateEnd, Long id) throws EntityNotFoundException;
}
