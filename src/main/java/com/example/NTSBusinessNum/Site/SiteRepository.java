package com.example.NTSBusinessNum.Site;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Integer> {
    Optional<Site> findByBusinessNum(String businessNum);
}
