package com.sparta.outsourcing.entity;

import com.sparta.outsourcing.enums.UserStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Restaurant extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String restaurantName;

    @Column(unique = true, nullable = false)
    private String info;

    private Long likeCount = 0L;

    private UserStatusEnum status = UserStatusEnum.ACTIVE;


}
