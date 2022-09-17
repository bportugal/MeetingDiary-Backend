package com.meetingdiary.service.meeting;

import com.meetingdiary.dataaccessobject.MeetingRepository;
import com.meetingdiary.dataaccessobject.PersonMetRepository;
import com.meetingdiary.dataaccessobject.UserRepository;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.PersonMetDO;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DefaultMeetingService implements MeetingService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultMeetingService.class);

    private final MeetingRepository meetingRepository;

    private final PersonMetRepository personMetRepository;

    private final UserRepository userRepository;

    @Autowired
    public DefaultMeetingService(final MeetingRepository meetingRepository, final PersonMetRepository personMetRepository, final UserRepository userRepository) {
        this.meetingRepository = meetingRepository;
        this.personMetRepository = personMetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MeetingDO findById(Long meetingId) throws EntityNotFoundException {
        return meetingRepository.findById(meetingId).orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + meetingId));
    }

    @Override
    @Transactional
    public void create(MeetingDO meetingDO) throws ConstraintsViolationException {
        try {
            UserDO user = userRepository.findByUsername(meetingDO.getUser().getUsername().toUpperCase());
            if (user == null) {
                throw new EntityNotFoundException("Manufacturer does not exist!");
            }
            meetingDO.setUser(user);
            MeetingDO meetingDO1 = meetingRepository.save(meetingDO);
            user.addMeeting(meetingDO1);
            checkIfAllPersonsAlreadyInDatabase(meetingDO.getPersonMetList(), meetingDO1);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating a meeting: {}", meetingDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    private void checkIfAllPersonsAlreadyInDatabase(List<PersonMetDO> personMetList, MeetingDO meetingDO) {
        CopyOnWriteArrayList<PersonMetDO> copyList = new CopyOnWriteArrayList<>(personMetList);
        for (PersonMetDO person : copyList) {
            person.addMeeting(meetingDO);
            PersonMetDO personCreated = null;
            if (personMetRepository.findByName(person.getName()) == null) {
                personCreated = personMetRepository.save(person);
            }
            meetingDO.addPerson(personCreated);
        }
    }

}
