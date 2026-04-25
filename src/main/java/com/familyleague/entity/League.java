package com.familyleague.entity;

import com.familyleague.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leagues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class League extends BaseEntity {


    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "sport_type", length = 50)
    @Builder.Default
    private String sportType = "CRICKET";

    @Column(name = "description", columnDefinition = "text")
    private String description;
}
