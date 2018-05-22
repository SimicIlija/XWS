package com.xml.booking.backendmain.lodging;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xml.booking.backendmain.comment.Comment;
import com.xml.booking.backendmain.users.User;


@Repository
public interface LodgingRepository extends JpaRepository<Lodging, Long> {


}
