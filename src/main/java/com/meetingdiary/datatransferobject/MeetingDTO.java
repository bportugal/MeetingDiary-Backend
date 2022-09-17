package com.meetingdiary.datatransferobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.meetingdiary.domainobject.PersonMetDO;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingDTO {

    @NotNull(message = "Username can not be null!")
    private LocalDateTime meetingDate;

    private UserDTOForMeeting user;

    private List<PersonMetDO> personList = new ArrayList<>();

    private MeetingDTO() {}

    private MeetingDTO(LocalDateTime meetingDate, UserDTOForMeeting user, List<PersonMetDO> personList) {
        this.meetingDate = meetingDate;
        this.user = user;
        this.personList = personList;
    }

    public static MeetingDTOBuilder newBuilder() {
        return new MeetingDTOBuilder();
    }


    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public UserDTOForMeeting getUser() {
        return user;
    }

    public List<PersonMetDO> getPersonList() {
        return personList;
    }

    public static class MeetingDTOBuilder {
        private LocalDateTime meetingDate;

        private UserDTOForMeeting userDTO;
        private List<PersonMetDO> personList = new ArrayList<>();

        public MeetingDTOBuilder setMeetingDate(LocalDateTime meetingDate) {
            this.meetingDate = meetingDate;
            return this;
        }

        public MeetingDTOBuilder setUserDTO(UserDTOForMeeting userDTO) {
            this.userDTO = userDTO;
            return this;
        }

        public MeetingDTOBuilder setPersonList(List<PersonMetDO> personList) {
            this.personList = personList;
            return this;
        }

        public MeetingDTO createMeetingDTO() {
            return new MeetingDTO(meetingDate, userDTO, personList);
        }
    }
}
