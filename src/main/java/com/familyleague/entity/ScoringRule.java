package com.familyleague.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scoring_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoringRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 50, unique = true)
    private String code;

    @Column(name = "description", length = 200)
    private String description;

    @Column(name = "points", nullable = false)
    private Integer points;
}
