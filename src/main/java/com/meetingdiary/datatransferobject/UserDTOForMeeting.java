package com.meetingdiary.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTOForMeeting {

    @NotNull(message = "Username can not be null!")
    private String username;

    private UserDTOForMeeting() {
    }

    public UserDTOForMeeting(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public static UserDTOForMeetingBuilder newBuilder() {
        return new UserDTOForMeetingBuilder();
    }

    public static class UserDTOForMeetingBuilder {

        private String username;

        public UserDTOForMeetingBuilder setUsername(String username) {
            this.username = username;
            return this;
        }


        public UserDTOForMeeting createUserDTOForMeeting() {
            return new UserDTOForMeeting(username);
        }
    }
}
