package com.recharged.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recharged.backend.entity.LocalUser;

@Repository
public interface UserRepository extends JpaRepository<LocalUser, Long> {
  Optional<LocalUser> findByUsername(String username);

  Optional<LocalUser> findByEmail(String email);
}