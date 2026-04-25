package com.familyleague.entity;

import com.familyleague.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admin_broadcast_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AdminBroadcastUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broadcast_id")
    private AdminBroadcast broadcast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
