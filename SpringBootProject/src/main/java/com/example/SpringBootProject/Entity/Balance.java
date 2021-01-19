package com.example.SpringBootProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long accountBalance;
    private long totalMoney;
    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;
}
