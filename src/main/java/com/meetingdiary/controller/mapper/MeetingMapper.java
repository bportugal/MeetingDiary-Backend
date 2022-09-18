package com.meetingdiary.controller.mapper;

import com.meetingdiary.datatransferobject.MeetingDTO;
import com.meetingdiary.datatransferobject.UserDTOForMeeting;
import com.meetingdiary.domainobject.MeetingDO;
import com.meetingdiary.domainobject.UserDO;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MeetingMapper {

    public static MeetingDO makeMeetingDO(MeetingDTO meetingDTO) {

        UserDO userDO = UserMapper.makeUserDOForMeeting(meetingDTO.getUser());

        return new MeetingDO(meetingDTO.getMeetingDate(), userDO, meetingDTO.getPersonName(), meetingDTO.getLatitude(), meetingDTO.getLongitude());
    }

    public static MeetingDTO makeMeetingDTO(MeetingDO meetingDO) {

        UserDTOForMeeting userDTO = UserMapper.makeUserDTOForMeeting(meetingDO.getUser());

        MeetingDTO.MeetingDTOBuilder dtoBuilder = MeetingDTO.newBuilder()
                .setMeetingDate(meetingDO.getMeetingDate())
                .setPersonName(meetingDO.getPersonName())
                .setUserDTO(userDTO)
                .setLatitude(meetingDO.getLatitude())
                .setLongitude(meetingDO.getLongitude());

        return dtoBuilder.createMeetingDTO();
    }

    public static List<MeetingDTO> makeMeetingDTOList(Collection<MeetingDO> meetings) {
        return meetings.stream()
                .map(MeetingMapper::makeMeetingDTO)
                .collect(Collectors.toList());
    }
}
