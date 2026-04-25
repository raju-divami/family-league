package com.familyleague.controller;

import com.familyleague.dto.response.AuditLogResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Audit")
@RestController
@RequestMapping("/api/audit")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ADMIN')")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    @Operation(summary = "Get audit logs for a record",
            description = "Returns the full audit trail for a specific record in a given table, paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Audit logs returned"),
            @ApiResponse(responseCode = "400", description = "Missing required query parameters"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<PagedResponse<AuditLogResponse>>> getLogsForRecord(
            @Parameter(description = "Table name (e.g. teams, matches)", example = "matches")
            @RequestParam String tableName,
            @Parameter(description = "Primary key of the record") @RequestParam Long recordId,
            Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                auditService.getLogsForRecord(tableName, recordId, pageable)));
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "Get audit logs by user",
            description = "Returns all audit entries where the change was made by the given user, paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Audit logs returned"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized — ADMIN role required"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<com.familyleague.dto.response.ApiResponseDto<PagedResponse<AuditLogResponse>>> getLogsByUser(
            @Parameter(description = "User ID") @PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(com.familyleague.dto.response.ApiResponseDto.ok(
                auditService.getLogsByUser(userId, pageable)));
    }
}
