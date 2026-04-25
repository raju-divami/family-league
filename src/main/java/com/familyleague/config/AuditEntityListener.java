package com.familyleague.config;

import com.familyleague.entity.BaseEntity;
import com.familyleague.security.UserPrincipal;
import com.familyleague.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JPA Entity Listener for automatic audit logging.
 * Captures INSERT, UPDATE, and DELETE operations on entities that extend BaseEntity.
 * 
 * Uses @PostPersist, @PostUpdate, and @PreRemove callbacks to log changes
 * to the audit_logs table via AuditService.
 */
@Slf4j
@Component
public class AuditEntityListener {

    private static AuditService auditService;
    private static ObjectMapper objectMapper;
    
    // ThreadLocal to store old entity state before update/delete
    private static final ThreadLocal<Map<Object, String>> oldDataStore = ThreadLocal.withInitial(HashMap::new);

    /**
     * Spring dependency injection for EntityListener.
     * Uses @Lazy to avoid circular dependency issues.
     */
    @Autowired
    public void setAuditService(@Lazy AuditService auditService) {
        AuditEntityListener.auditService = auditService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        AuditEntityListener.objectMapper = objectMapper;
    }

    /**
     * Called after entity is persisted (INSERT).
     */
    @PostPersist
    public void onPostPersist(Object entity) {
        if (!(entity instanceof BaseEntity baseEntity)) {
            return;
        }
        
        try {
            String tableName = getTableName(entity);
            Long recordId = baseEntity.getId();
            String newData = serializeEntity(entity);
            Long changedBy = getCurrentUserId();
            
            if (auditService != null) {
                auditService.log(tableName, recordId, "INSERT", null, newData, changedBy);
                log.debug("Audit log created for INSERT: table={}, recordId={}", tableName, recordId);
            }
        } catch (Exception e) {
            log.error("Error creating audit log for INSERT operation", e);
        }
    }

    /**
     * Called before entity is updated to capture old state.
     */
    @PreUpdate
    public void onPreUpdate(Object entity) {
        if (!(entity instanceof BaseEntity)) {
            return;
        }
        
        try {
            String oldData = serializeEntity(entity);
            oldDataStore.get().put(entity, oldData);
        } catch (Exception e) {
            log.error("Error capturing old data for UPDATE operation", e);
        }
    }

    /**
     * Called after entity is updated (UPDATE).
     */
    @PostUpdate
    public void onPostUpdate(Object entity) {
        if (!(entity instanceof BaseEntity baseEntity)) {
            return;
        }
        
        try {
            String tableName = getTableName(entity);
            Long recordId = baseEntity.getId();
            String oldData = oldDataStore.get().remove(entity);
            String newData = serializeEntity(entity);
            Long changedBy = getCurrentUserId();
            
            if (auditService != null) {
                auditService.log(tableName, recordId, "UPDATE", oldData, newData, changedBy);
                log.debug("Audit log created for UPDATE: table={}, recordId={}", tableName, recordId);
            }
        } catch (Exception e) {
            log.error("Error creating audit log for UPDATE operation", e);
        } finally {
            oldDataStore.get().remove(entity);
        }
    }

    /**
     * Called before entity is removed to capture state before deletion.
     */
    @PreRemove
    public void onPreRemove(Object entity) {
        if (!(entity instanceof BaseEntity baseEntity)) {
            return;
        }
        
        try {
            String tableName = getTableName(entity);
            Long recordId = baseEntity.getId();
            String oldData = serializeEntity(entity);
            Long changedBy = getCurrentUserId();
            
            if (auditService != null) {
                auditService.log(tableName, recordId, "DELETE", oldData, null, changedBy);
                log.debug("Audit log created for DELETE: table={}, recordId={}", tableName, recordId);
            }
        } catch (Exception e) {
            log.error("Error creating audit log for DELETE operation", e);
        }
    }

    /**
     * Gets the table name from the @Table annotation or entity class name.
     */
    private String getTableName(Object entity) {
        Class<?> entityClass = entity.getClass();
        
        // Check for @Table annotation
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (table.name() != null && !table.name().isEmpty()) {
                return table.name();
            }
        }
        
        // Fallback to entity class simple name
        return entityClass.getSimpleName();
    }

    /**
     * Serializes entity to JSON string.
     */
    private String serializeEntity(Object entity) {
        try {
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
                objectMapper.findAndRegisterModules();
            }
            return objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            log.error("Error serializing entity to JSON", e);
            return "{}";
        }
    }

    /**
     * Gets the current authenticated user ID from SecurityContext.
     */
    private Long getCurrentUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof UserPrincipal principal) {
                return principal.getId();
            }
        } catch (Exception e) {
            log.debug("Could not retrieve current user ID", e);
        }
        return null;
    }
}
