package com.meetingdiary.service.meeting;

import com.meetingdiary.dataaccessobject.MeetingRepository;
import com.meetingdiary.dataaccessobject.UserRepository;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultMeetingService implements MeetingService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultMeetingService.class);

    private final MeetingRepository meetingRepository;

    private final UserRepository userRepository;

    @Autowired
    public DefaultMeetingService(final MeetingRepository meetingRepository, final UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeetingDO findById(Long meetingId) throws EntityNotFoundException {
        return meetingRepository.findById(meetingId).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + meetingId));
    }

    @Override
    @Transactional
    public void create(List<MeetingDO> meetingDOList) throws ConstraintsViolationException, EntityNotFoundException {
        try {
            UserDO user = userRepository.findByUsername(meetingDOList.get(0).getUser().getUsername().toUpperCase());
            if (user == null) {
                throw new EntityNotFoundException("User does not exist!");
            }
            for (MeetingDO meetingDO : meetingDOList) {
                checkMeetingExceptions(meetingDO);
                meetingDO.setUser(user);
            }
            List<MeetingDO> meetingDO1 = meetingRepository.saveAll(meetingDOList);
            //user.addMeeting(meetingDO1);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a meeting: {}", meetingDOList, e);
            throw new ConstraintsViolationException("Meeting already exists");
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    private void checkMeetingExceptions(MeetingDO meetingDO) throws ConstraintsViolationException {
        if (meetingDO.getMeetingDate() == null) {
            throw new ConstraintsViolationException("Can not create meeting without date");
        }
        if (meetingDO.getPersonName().isEmpty()) {
            throw new ConstraintsViolationException("Can not create meeting without person");
        }
        if (meetingDO.getLatitude() == null) {
            throw new ConstraintsViolationException("Can not create meeting without latitude of the meeting");
        }
        if (meetingDO.getLongitude() == null) {
            throw new ConstraintsViolationException("Can not create meeting without longitude of the meeting");
        }
    }

    @Override
    public List<MeetingDO> findMeetingBetweenDates(LocalDateTime meetingDateStart, LocalDateTime meetingDateEnd, Long id) throws EntityNotFoundException {
        try {
            return meetingRepository.findAllByMeetingDateBetweenAndUserId(meetingDateStart, meetingDateEnd, id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User doesn't exist.");
        }
    }

    @Override
    public List<MeetingDO> findUserMeetings(Long userId) throws EntityNotFoundException {
        try {
            return meetingRepository.findByUserId(userId);
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException("User doesn't exist.");
        }
    }

}
