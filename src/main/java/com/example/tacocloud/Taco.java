package com.example.tacocloud;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Taco {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "createdat")
    private Date createdAt;

    @NotNull
    @Size(min = 5, message = "should be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "we refuse to ship empty tacos")
    @OneToMany
    @ToString.Exclude
    private List<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Taco taco = (Taco) o;
        return Objects.equals(id, taco.id);
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
