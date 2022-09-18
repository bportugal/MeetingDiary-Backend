package com.meetingdiary.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import springfox.documentation.spring.web.json.Json;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Username can not be null!")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private UserDTO() {
    }

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
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

        private Long id;

        private String username;

        public UserDTOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UserDTOBuilder setUsername(String username) {
            this.username = username;
            return this;
        }


        public UserDTO createUserDTO() {
            return new UserDTO(id, username);
        }
    }
}
