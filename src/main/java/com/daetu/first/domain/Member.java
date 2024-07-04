package com.daetu.first.domain;

// ORM

import jakarta.persistence.*;

@Entity //jpa가 관리하는 entity가 되는 거임.
public class Member {
    //

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
