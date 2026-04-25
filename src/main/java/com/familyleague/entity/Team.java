package com.familyleague.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team extends BaseEntity {

    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "short_name", length = 20)
    private String shortName;

    @Column(name = "logo_url", length = 500)
    private String logoUrl;

    @Column(name = "deleted", nullable = false)
    @Builder.Default
    private boolean deleted = false;
}
