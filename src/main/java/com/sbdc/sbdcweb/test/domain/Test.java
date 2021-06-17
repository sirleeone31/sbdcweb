package com.sbdc.sbdcweb.test.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Test_Date")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String date;

    public Test() {
        this.date = String.valueOf(LocalDateTime.now());
    }
}
