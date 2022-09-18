package com.meetingdiary.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingDTO {

    @NotNull(message = "Meeting date can not be null!")
    private LocalDateTime meetingDate;

    private UserDTOForMeeting user;

    private String personName;

    private Double latitude;

    private Double longitude;

    private MeetingDTO() {}

    private MeetingDTO(LocalDateTime meetingDate, UserDTOForMeeting user, String personName, Double latitude, Double longitude) {
        this.meetingDate = meetingDate;
        this.user = user;
        this.personName = personName;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getPersonName() {
        return personName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public static class MeetingDTOBuilder {
        private LocalDateTime meetingDate;

        private UserDTOForMeeting userDTO;

        private String personName;

        private Double latitude;

        private Double longitude;

        public MeetingDTOBuilder setMeetingDate(LocalDateTime meetingDate) {
            this.meetingDate = meetingDate;
            return this;
        }

        public MeetingDTOBuilder setUserDTO(UserDTOForMeeting userDTO) {
            this.userDTO = userDTO;
            return this;
        }

        public MeetingDTOBuilder setPersonName(String personName) {
            this.personName = personName;
            return this;
        }

        public MeetingDTOBuilder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public MeetingDTOBuilder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public MeetingDTO createMeetingDTO() {
            return new MeetingDTO(meetingDate, userDTO, personName, latitude, longitude);
        }
    }
}
