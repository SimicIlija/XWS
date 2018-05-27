package com.xml.booking.backendmain.lodging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LodgingRepository extends JpaRepository<Lodging, Long> {
    List<Lodging> findByLocationIgnoreCase(String location);
}
