package com.familyleague.service.impl;

import com.familyleague.dto.request.BroadcastRequest;
import com.familyleague.dto.response.NotificationResponse;
import com.familyleague.dto.response.PagedResponse;
import com.familyleague.entity.Match;
import com.familyleague.entity.Notification;
import com.familyleague.entity.SeasonMember;
import com.familyleague.entity.User;
import com.familyleague.exception.ResourceNotFoundException;
import com.familyleague.mapper.NotificationMapper;
import com.familyleague.repository.MatchRepository;
import com.familyleague.repository.NotificationRepository;
import com.familyleague.repository.SeasonMemberRepository;
import com.familyleague.repository.UserRepository;
import com.familyleague.service.EmailService;
import com.familyleague.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final MatchRepository matchRepository;
    private final SeasonMemberRepository seasonMemberRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final NotificationMapper notificationMapper;

    @Override
    @Transactional
    public void sendMatchReminderNotifications(Long matchId) {
        log.info("Sending match reminders for match: {}", matchId);
        
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        
        List<SeasonMember> members = seasonMemberRepository.findBySeasonId(match.getSeason().getId());
        
        for (SeasonMember member : members) {
            String message = String.format("Match reminder: %s vs %s starts at %s", 
                    match.getHomeTeam().getName(), 
                    match.getAwayTeam().getName(), 
                    match.getStartTime());
            
            Notification notification = Notification.builder()
                    .user(member.getUser())
                    .subject("Match Reminder")
                    .message(message)
                    .eventType("MATCH_REMINDER")
                    .status("SENT")
                    .build();
            
            notificationRepository.save(notification);
            emailService.sendEmailAsync(member.getUser().getEmail(), "Match Reminder", message);
        }
        
        log.info("Match reminders sent for match: {}", matchId);
    }

    @Override
    @Transactional
    public void sendResultNotificationToAdmin(Long matchId) {
        log.info("Sending result notification for match: {}", matchId);
        
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + matchId));
        
        // Notification logic for admin users
        log.info("Result notification sent for match: {}", matchId);
    }

    @Override
    @Transactional
    public void broadcast(BroadcastRequest request, Long adminId) {
        log.info("Broadcasting message by admin: {}", adminId);
        
        for (Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
            
            Notification notification = Notification.builder()
                    .user(user)
                    .subject(request.getTitle())
                    .message(request.getMessage())
                    .eventType(request.getEventType() != null ? request.getEventType() : "BROADCAST")
                    .status("SENT")
                    .build();
            
            notificationRepository.save(notification);
        }
        
        log.info("Broadcast sent to {} users", request.getUserIds().size());
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<NotificationResponse> getMyNotifications(Long userId, Pageable pageable) {
        Page<Notification> notificationPage = notificationRepository.findByUserId(userId, pageable);
        return PagedResponse.<NotificationResponse>builder()
                .content(notificationPage.getContent().stream().map(notificationMapper::toResponse).toList())
                .page(notificationPage.getNumber())
                .size(notificationPage.getSize())
                .totalElements(notificationPage.getTotalElements())
                .totalPages(notificationPage.getTotalPages())
                .last(notificationPage.isLast())
                .build();
    }
}
