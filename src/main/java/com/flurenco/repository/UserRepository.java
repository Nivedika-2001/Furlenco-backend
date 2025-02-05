package com.flurenco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.flurenco.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
