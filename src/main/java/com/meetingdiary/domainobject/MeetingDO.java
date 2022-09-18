package com.meetingdiary.domainobject;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Meetings")
public class MeetingDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @NotNull
    private LocalDateTime meetingDate;

    /*@ManyToMany(mappedBy = "meetings")*/
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private UserDO user;

    @NotNull(message = "Name can not be null!")
    private String personName;

    @NotNull(message = "latitude can not be null!")
    private Double latitude;

    @NotNull(message = "longitude can not be null!")
    private Double longitude;

    private MeetingDO() {
    }

    public MeetingDO(LocalDateTime meetingDate, UserDO user, String personName, Double latitude, Double longitude) {
        this.meetingDate = meetingDate;
        this.user = user;
        this.personName = personName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(LocalDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
