package com.enotes.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
