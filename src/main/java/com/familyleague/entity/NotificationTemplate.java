package com.familyleague.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_templates")
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
    private String channel = "EMAIL";

    public NotificationTemplate() {
    }

    public NotificationTemplate(Long id, String code, String subject, String body, String channel) {
        this.id = id;
        this.code = code;
        this.subject = subject;
        this.body = body;
        this.channel = channel;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String code;
        private String subject;
        private String body;
        private String channel = "EMAIL";

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public NotificationTemplate build() {
            NotificationTemplate obj = new NotificationTemplate();
            obj.id = this.id;
            obj.code = this.code;
            obj.subject = this.subject;
            obj.body = this.body;
            obj.channel = this.channel;
            return obj;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
