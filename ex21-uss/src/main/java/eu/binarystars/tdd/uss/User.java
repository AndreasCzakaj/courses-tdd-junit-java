package eu.binarystars.tdd.uss;

import jakarta.persistence.*;
import lombok.*;

import java.beans.ConstructorProperties;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder(toBuilder=true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean acceptedTerms;
}