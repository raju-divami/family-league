package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_broadcast_users")
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

    public AdminBroadcastUser() {
    }

    public AdminBroadcastUser(Long id, AdminBroadcast broadcast, User user) {
        this.id = id;
        this.broadcast = broadcast;
        this.user = user;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private AdminBroadcast broadcast;
        private User user;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder broadcast(AdminBroadcast broadcast) {
            this.broadcast = broadcast;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public AdminBroadcastUser build() {
            AdminBroadcastUser obj = new AdminBroadcastUser();
            obj.id = this.id;
            obj.broadcast = this.broadcast;
            obj.user = this.user;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdminBroadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(AdminBroadcast broadcast) {
        this.broadcast = broadcast;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
