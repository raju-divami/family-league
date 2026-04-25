package com.familyleague.dto.response;

public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String displayName;
    private String avatarName;
    private String profileImageUrl;

    private UserProfileResponse(Long id, Long userId, String displayName, String avatarName, String profileImageUrl) {
        this.id = id;
        this.userId = userId;
        this.displayName = displayName;
        this.avatarName = avatarName;
        this.profileImageUrl = profileImageUrl;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getDisplayName() { return displayName; }
    public String getAvatarName() { return avatarName; }
    public String getProfileImageUrl() { return profileImageUrl; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private Long userId;
        private String displayName;
        private String avatarName;
        private String profileImageUrl;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder displayName(String displayName) { this.displayName = displayName; return this; }
        public Builder avatarName(String avatarName) { this.avatarName = avatarName; return this; }
        public Builder profileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; return this; }

        public UserProfileResponse build() {
            return new UserProfileResponse(id, userId, displayName, avatarName, profileImageUrl);
        }
    }
}
