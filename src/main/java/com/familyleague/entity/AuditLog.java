package com.familyleague.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)

public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "action", length = 20)
    private String action;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "old_data", columnDefinition = "json")
    private String oldData;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "new_data", columnDefinition = "json")
    private String newData;

    @Column(name = "changed_by")
    private Long changedBy;

    @CreatedDate
    @Column(name = "changed_at", updatable = false)
    private LocalDateTime changedAt;
}
