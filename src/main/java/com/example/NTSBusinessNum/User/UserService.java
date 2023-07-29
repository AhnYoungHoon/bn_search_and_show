package com.example.NTSBusinessNum.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserToSiteRepository userToSiteRepository;
    private final ReportedSiteRepository reportedSiteRepository;
    private final PasswordEncoder passwordEncoder;


    public SiteUser create(String username, String email, String password){
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public void report(String username, String url, String businessNum){
        ReportedSite reportedSite = new ReportedSite();
        reportedSite.setUsername(username);
        reportedSite.setBusinessNum(businessNum);
        reportedSite.setUrl(url);
        this.reportedSiteRepository.save(reportedSite);
    }

//    public Long findUserId(String username){
//        Optional<SiteUser> user = userRepository.findByusername(username);
//        Long userId = user.get().getId();
//        System.out.println(userId);
//
//        return userId;
//    }

}
