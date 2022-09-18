package com.meetingdiary.service.user;

import com.meetingdiary.dataaccessobject.UserRepository;
import com.meetingdiary.domainobject.UserDO;
import com.meetingdiary.exception.ConstraintsViolationException;
import com.meetingdiary.exception.EmailInvalidException;
import com.meetingdiary.exception.EntityNotFoundException;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

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
        UserDO user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User does not exist!");
        } else {
            return user;
        }
    }

    @Override
    public UserDO create(UserDO userDO) throws ConstraintsViolationException, EmailInvalidException {
        UserDO newUser;
        if (userDO.getUsername() == null || userDO.getPassword() == null) {
            throw new DataIntegrityViolationException("Email and/or Passoword are missing");
        }
        if (!EmailValidator.getInstance().isValid(userDO.getUsername())) {
            throw new EmailInvalidException("Email has an invalid format.");
        }
        try {
            newUser = userRepository.save(userDO);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("ConstraintsViolationException while creating an user: {}", userDO, e);
            throw new ConstraintsViolationException("Email already being used.");
        }
        return newUser;
    }

    @Override
    public UserDO login(UserDO userDO) throws EntityNotFoundException, EmailInvalidException {
        UserDO user;
        if (userDO.getUsername() == null || userDO.getPassword() == null) {
            throw new DataIntegrityViolationException("Email and/or Passoword are missing");
        }
        if (!EmailValidator.getInstance().isValid(userDO.getUsername())) {
            throw new EmailInvalidException("Email has an invalid format.");
        }
        user = userRepository.findByUsername(userDO.getUsername());
        if (user == null) {
            throw new EntityNotFoundException("User does not exist!");
        }
        return user;
    }
}
