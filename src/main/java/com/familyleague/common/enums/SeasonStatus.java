package com.familyleague.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Enumeration of valid season statuses and their allowed transitions.
 * 
 * Status flow:
 * DRAFT -> OPEN -> LOCKED -> ACTIVE -> COMPLETED -> CLOSED
 */
public enum SeasonStatus {
    
    /**
     * Initial state when season is created
     */
    DRAFT,
    
    /**
     * Season is open for user enrollment and predictions
     */
    OPEN,
    
    /**
     * Season predictions are locked (before first match)
     */
    LOCKED,
    
    /**
     * Season is currently active with ongoing matches
     */
    ACTIVE,
    
    /**
     * All matches completed, awaiting final closure
     */
    COMPLETED,
    
    /**
     * Season permanently closed, no further changes allowed
     */
    CLOSED;
    
    // Define valid status transitions
    private static final Map<SeasonStatus, List<SeasonStatus>> VALID_TRANSITIONS = Map.of(
        DRAFT, Arrays.asList(OPEN),
        OPEN, Arrays.asList(LOCKED, ACTIVE),
        LOCKED, Arrays.asList(ACTIVE),
        ACTIVE, Arrays.asList(COMPLETED),
        COMPLETED, Arrays.asList(CLOSED)
    );
    
    /**
     * Check if transition from current status to target status is valid.
     * 
     * @param targetStatus The status to transition to
     * @return true if transition is allowed, false otherwise
     */
    public boolean canTransitionTo(SeasonStatus targetStatus) {
        if (this == CLOSED) {
            return false; // No transitions allowed from CLOSED
        }
        List<SeasonStatus> allowedTransitions = VALID_TRANSITIONS.get(this);
        return allowedTransitions != null && allowedTransitions.contains(targetStatus);
    }
    
    /**
     * Get list of valid next statuses from current status.
     * 
     * @return List of allowed transition statuses
     */
    public List<SeasonStatus> getValidTransitions() {
        return VALID_TRANSITIONS.getOrDefault(this, List.of());
    }
}
