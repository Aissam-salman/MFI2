package com.app.mfi2.user.model;

import com.app.mfi2.model.Product;
import com.app.mfi2.user.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Producer extends User {
    @Size(min = 9, max = 9)
    @Column(length = 9)
    @NotNull
    private String siret;

    @OneToMany(mappedBy = "producerOwner")
    private List<Product> products;
}
