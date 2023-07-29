package com.example.NTSBusinessNum.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@Entity
@ToString
public class ReportedSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String username;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(length = 200)
    private String businessNum;
}
