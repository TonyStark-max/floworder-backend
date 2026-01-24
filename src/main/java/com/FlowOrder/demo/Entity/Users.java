package com.FlowOrder.demo.Entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles role;

    public Users(String email, String password, Roles role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
