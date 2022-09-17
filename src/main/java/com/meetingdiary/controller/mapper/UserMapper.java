package com.meetingdiary.controller.mapper;

import com.meetingdiary.datatransferobject.UserDTO;
import com.meetingdiary.datatransferobject.UserDTOForMeeting;
import com.meetingdiary.domainobject.UserDO;

public class UserMapper {

    public static UserDO makeUserDO(UserDTO userDTO) {

        UserDO user = new UserDO(userDTO.getUsername().toUpperCase());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public static UserDO makeUserDOForMeeting(UserDTOForMeeting userDTOForMeeting) {

        return new UserDO(userDTOForMeeting.getUsername().toUpperCase());
    }

    public static UserDTO makeUserDTO(UserDO userDO) {

        UserDTO.UserDTOBuilder dtoBuilder = UserDTO.newBuilder()
                .setUsername(userDO.getUsername());

        return dtoBuilder.createUserDTO();
    }

    public static UserDTOForMeeting makeUserDTOForMeeting(UserDO userDO) {

        UserDTOForMeeting.UserDTOForMeetingBuilder dtoBuilder = UserDTOForMeeting.newBuilder()
                .setUsername(userDO.getUsername());

        return dtoBuilder.createUserDTOForMeeting();
    }
}
