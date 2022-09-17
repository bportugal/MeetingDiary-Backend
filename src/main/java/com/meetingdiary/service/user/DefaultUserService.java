package com.meetingdiary.service.user;

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

import java.util.List;

@Service
public class DefaultUserService implements UserService{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultUserService.class);

    private final UserRepository userRepository;

    @Autowired
    public DefaultUserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDO findById(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + userId));
    }

    @Override
    public UserDO findByUsername(String username) throws EntityNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<MeetingDO> findMeetings(Long userId) throws EntityNotFoundException {
        UserDO userDO = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + userId));
        return userDO.getMeetings();
    }

    @Override
    public void create(UserDO userDO) throws ConstraintsViolationException {
        try {
            //checkIfAllPersonAlreadyInDatabase(meetingDO.getPersonMetList());
            userRepository.save(userDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a meeting: {}", userDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
    }
}
