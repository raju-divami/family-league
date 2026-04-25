package com.familyleague.entity;

import com.familyleague.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private NotificationTemplate template;

    @Column(name = "event_type", length = 100)
    private String eventType;

    @Column(name = "subject", length = 250)
    private String subject;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "status", length = 30)
    @Builder.Default
    private String status = "PENDING";

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "failure_reason", columnDefinition = "text")
    private String failureReason;
}
