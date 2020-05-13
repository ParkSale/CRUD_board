package com.example.demo.repository;

import com.example.demo.domain.Follow;
import com.example.demo.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowRepository extends CrudRepository<Follow,Long> {

}
