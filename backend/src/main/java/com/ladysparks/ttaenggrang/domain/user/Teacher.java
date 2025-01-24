package com.ladysparks.ttaenggrang.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Teacher {

    @Id
    private Long id;

}
