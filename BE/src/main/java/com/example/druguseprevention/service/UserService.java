package com.example.druguseprevention.service;

import com.example.druguseprevention.dto.ProfileDTO;
import com.example.druguseprevention.entity.User;
import com.example.druguseprevention.enums.Gender;
import com.example.druguseprevention.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ProfileDTO getProfileById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToDTO(user);
    }

    public void updateProfile(ProfileDTO dto) {
        User user = getCurrentUser();
        user.setFullName(dto.getFullName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAddress(dto.getAddress());

        if (dto.getDateOfBirth() != null) {
            user.setDateOfBirth(dto.getDateOfBirth());
        }

        if (dto.getGender() != null) {
            user.setGender(dto.getGender());
        }

        userRepository.save(user);
    }

    public ProfileDTO getProfile() {
        User user = getCurrentUser();
        return mapToDTO(user);
    }

    public List<ProfileDTO> getAllProfiles() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ProfileDTO mapToDTO(User user) {
        ProfileDTO dto = new ProfileDTO();
        dto.setFullName(user.getFullName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAddress(user.getAddress());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setGender(user.getGender());
        return dto;
    }
    public void deleteProfileById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
