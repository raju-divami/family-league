package com.familyleague.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "code", length = 100, unique = true)
    private String code;

    @Column(name = "subject", length = 250)
    private String subject;

    @Column(name = "body", columnDefinition = "text")
    private String body;

    @Column(name = "channel", length = 30)
    @Builder.Default
    private String channel = "EMAIL";
}
