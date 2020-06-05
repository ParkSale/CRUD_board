package com.example.demo.repository;

import com.example.demo.domain.Notice;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepository extends CrudRepository<Notice,Long> {
    
}
