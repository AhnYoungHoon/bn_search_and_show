package com.example.NTSBusinessNum.User;

import com.example.NTSBusinessNum.Site.Site;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserToSite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Site site;

    @ManyToOne
    private SiteUser user;
}
