package com.meetingdiary.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull(message = "Username can not be null!")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private UserDTO() {
    }

    public UserDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static UserDTOBuilder newBuilder() {
        return new UserDTOBuilder();
    }

    public static class UserDTOBuilder {

        private String username;

        public UserDTOBuilder setUsername(String username) {
            this.username = username;
            return this;
        }


        public UserDTO createUserDTO() {
            return new UserDTO(username);
        }
    }
}
