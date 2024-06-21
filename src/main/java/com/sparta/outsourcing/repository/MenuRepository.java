package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  MenuRepository extends JpaRepository<Menu, Integer> {

}
