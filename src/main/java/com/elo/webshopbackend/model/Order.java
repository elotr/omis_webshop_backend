package com.elo.webshopbackend.model;

import com.elo.webshopbackend.annotations.ValueFalseAnnotation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    BigDecimal totalSum;

    @JsonProperty
    //@ValueFalseAnnotation (isPaid = false)
    private Boolean isPaid;

    @OneToOne
    @NotNull
    private Person person;

    @ManyToMany() // mitu tabelit, listi jaoks...
    private List<Item> items;

}