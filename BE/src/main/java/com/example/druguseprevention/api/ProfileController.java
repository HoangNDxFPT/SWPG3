package com.example.druguseprevention.api;

import com.example.druguseprevention.dto.ProfileDTO;
import com.example.druguseprevention.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Lấy profile người hiện tại (dựa trên token)
    @SecurityRequirement(name = "api")
    @GetMapping
    public ResponseEntity<ProfileDTO> getCurrentProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    // ✅ Cập nhật profile người hiện tại
    @SecurityRequirement(name = "api")
    @PutMapping
    public ResponseEntity<?> updateCurrentProfile(@RequestBody ProfileDTO dto) {
        userService.updateProfile(dto);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    // ✅ Lấy profile của user bất kỳ theo ID
    @SecurityRequirement(name = "api")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfileById(id));
    }

    // ✅ Lấy danh sách tất cả user (chỉ ADMIN được phép)
    @SecurityRequirement(name = "api")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() {
        return ResponseEntity.ok(userService.getAllProfiles());
    }

    // ✅ Xóa user theo ID (chỉ ADMIN được phép)
    @SecurityRequirement(name = "api")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        userService.deleteProfileById(id);
        return ResponseEntity.ok("Profile deleted successfully.");
    }
}
