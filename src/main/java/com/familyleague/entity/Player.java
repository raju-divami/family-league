package com.familyleague.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player extends BaseEntity {

    @Column(name = "code", length = 50, unique = true)
    private String code;

    @Column(name = "full_name", length = 150, nullable = false)
    private String fullName;

    @Column(name = "short_name", length = 100)
    private String shortName;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}
