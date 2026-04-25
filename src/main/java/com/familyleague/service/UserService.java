package com.familyleague.service;

import com.familyleague.dto.response.PagedResponse;
import com.familyleague.dto.request.UpdateProfileRequest;
import com.familyleague.dto.response.UserProfileResponse;
import com.familyleague.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponse getUserById(Long id);

    UserProfileResponse getProfile(Long userId);

    UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request);

    PagedResponse<UserResponse> getAllUsers(Pageable pageable);

    void deactivateUser(Long id);
}
