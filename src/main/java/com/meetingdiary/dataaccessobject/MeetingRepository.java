package com.meetingdiary.dataaccessobject;

import com.meetingdiary.domainobject.MeetingDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<MeetingDO, Long> {
    //List<MeetingDO> findAllByMeetingDateBetweenAndUserId(LocalDateTime meetingDateStart, LocalDateTime meetingDateEnd);
}
