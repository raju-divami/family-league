package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.BroadcastRequest;
import com.familyleague.dto.response.NotificationResponse;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    void sendMatchReminderNotifications(Long matchId);

    void sendPredictionReminderToUser(Long matchId, Long userId);

    void sendResultNotificationToAdmin(Long matchId);

    void broadcast(BroadcastRequest request, Long adminId);

    PagedResponse<NotificationResponse> getMyNotifications(Long userId, Pageable pageable);
}
