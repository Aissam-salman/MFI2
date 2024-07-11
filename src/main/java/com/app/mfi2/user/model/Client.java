package com.app.mfi2.user.model;


import com.app.mfi2.user.Role;
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
public class Client extends User {
}
