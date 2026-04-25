package com.familyleague.mapper;

import com.familyleague.dto.request.UpdateProfileRequest;
import com.familyleague.dto.response.UserProfileResponse;
import com.familyleague.dto.response.UserResponse;
import com.familyleague.entity.User;
import com.familyleague.entity.UserProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    @Mapping(target = "userId", source = "user.id")
    UserProfileResponse toProfileResponse(UserProfile profile);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfileFromRequest(UpdateProfileRequest request, @MappingTarget UserProfile profile);
}
