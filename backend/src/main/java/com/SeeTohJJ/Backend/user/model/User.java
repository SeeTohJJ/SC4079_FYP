package com.SeeTohJJ.Backend.user.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    public enum Role {
        USER,
        ADMIN
    }

    // I have reserved UserId 1 - 100 for other purposes
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(
            name = "user_seq_gen",
            sequenceName = "user_seq",
            allocationSize = 1,
            initialValue = 101
    )
    private int userId;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID publicUserId;

    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String resetToken;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User() {
        this.publicUserId = UUID.randomUUID();
        this.role = Role.USER;
        this.createdAt = LocalDateTime.now();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;

        this.publicUserId = UUID.randomUUID();
        this.role = Role.USER;
        this.createdAt = LocalDateTime.now();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int UserId) {
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
