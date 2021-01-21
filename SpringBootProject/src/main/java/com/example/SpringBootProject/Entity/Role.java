package com.example.SpringBootProject.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    private long id;
    private String rolename;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<User> users;
}
