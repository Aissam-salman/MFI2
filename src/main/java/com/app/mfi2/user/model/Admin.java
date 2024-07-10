package com.app.mfi2.user;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {
    public Admin(String firstname, String lastname, String email, String password, String adminSpecificInfo) {
        super(firstname, lastname, email, password, Role.ADMIN);
    }
}
