package my.rest.restController.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;
}
