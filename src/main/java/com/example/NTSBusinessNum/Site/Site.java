package com.example.NTSBusinessNum.Site;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@ToString
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(length = 200)
    private String businessNum;

    @Column(length = 200)
    private String state;

    private LocalDateTime searchDate;

}
