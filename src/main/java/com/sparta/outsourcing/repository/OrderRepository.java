package com.sparta.outsourcing.repository;

import com.sparta.outsourcing.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 9e993afb4ad9379a8a5efc1420377b971f931cce
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
