package com.meetingdiary.dataaccessobject;

import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<UserDO, Long> {
    //List<UserDO> findByMeetingDate_GreaterThanEqualAndEndDateTime_LessThanEqual(LocalDateTime startDateTime, LocalDateTime endDateTime);

    UserDO findByUsername(String username);
}
