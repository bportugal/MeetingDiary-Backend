package com.meetingdiary.domainobject;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "meetings", fetch = FetchType.EAGER)
    @NotNull
    private List<PersonMetDO> personMetList = new ArrayList<>();

    private MeetingDO() {
    }

    public MeetingDO(LocalDateTime meetingDate, UserDO user, List<PersonMetDO> personMetList) {
        this.meetingDate = meetingDate;
        this.user = user;
        this.personMetList = personMetList;
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

    public List<PersonMetDO> getPersonMetList() {
        return personMetList;
    }

    public void addPerson(PersonMetDO personMetDO) {
        this.personMetList.add(personMetDO);
    }
}
