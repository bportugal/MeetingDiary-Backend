package com.meetingdiary.dataaccessobject;

import com.meetingdiary.domainobject.PersonMetDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonMetRepository extends JpaRepository<PersonMetDO, Long> {

    PersonMetDO findByName(String name);
}
