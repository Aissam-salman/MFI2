package com.app.mfi2.user.model;

import com.app.mfi2.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Producer.
 */
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

    private EProducerStatus status;

    @Override
    public String toString() {
        return "Producer{" +
                "siret='" + siret + '\'' +
                '}';
    }
}
