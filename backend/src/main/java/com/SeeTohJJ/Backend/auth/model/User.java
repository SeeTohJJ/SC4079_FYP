package com.SeeTohJJ.Backend.auth.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    public enum Role {
        USER,
        ADMIN
    }

    // I have reserved UserId 1 - 100 for other purposes
    private Long userId;

    private UUID publicUserId;
    private String email;
    private String password;
    private Role role;
    private String resetToken;
    private LocalDateTime createdAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long UserId) {
        this.userId = UserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public UUID getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(UUID publicUserId) {
        this.publicUserId = publicUserId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
