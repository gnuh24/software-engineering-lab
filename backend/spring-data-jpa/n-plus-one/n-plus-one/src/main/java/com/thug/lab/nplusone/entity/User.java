package com.thug.lab.nplusone.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    private Long id;

    private String name;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
//  @BatchSize(size = 5)
    private List<UserOrder> orders = new ArrayList<>();

}
