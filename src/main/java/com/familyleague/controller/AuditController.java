package com.familyleague.controller;

import com.familyleague.dto.response.AuditLogResponse;
import com.familyleague.service.AuditService;
import com.familyleague.dto.response.ApiResponse;
import com.familyleague.dto.response.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Audit")
@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    @Operation(summary = "Get audit logs for a record")
    public ResponseEntity<ApiResponse<PagedResponse<AuditLogResponse>>> getLogsForRecord(
            @RequestParam String tableName,
            @RequestParam Long recordId,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(auditService.getLogsForRecord(tableName, recordId, pageable)));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get audit logs by user")
    public ResponseEntity<ApiResponse<PagedResponse<AuditLogResponse>>> getLogsByUser(
            @PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.ok(auditService.getLogsByUser(userId, pageable)));
    }
}
