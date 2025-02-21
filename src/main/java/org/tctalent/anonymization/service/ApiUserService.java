package org.tctalent.anonymization.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import org.tctalent.anonymization.domain.entity.ApiUser;
import org.tctalent.anonymization.dto.request.ApiUserRegistrationRequest;
import org.tctalent.anonymization.dto.response.ApiUserRegistrationResponse;
import org.tctalent.anonymization.repository.ApiUserRepository;
import org.tctalent.anonymization.security.ApiKeyGenerator;

@Service
@RequiredArgsConstructor
public class ApiUserService {

  private final ApiUserRepository apiUserRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public ApiUserRegistrationResponse registerApiUser(ApiUserRegistrationRequest request) {
    // Generate the plain API key
    String plainApiKey = ApiKeyGenerator.generateApiKey();
    // Hash the API key for secure storage
    String hashedKey = passwordEncoder.encode(plainApiKey);

    // Create the new API user entity
    ApiUser apiUser = ApiUser.builder()
        .ownerName(request.getOwnerName())
        .email(request.getEmail())
        .apiKeyHash(hashedKey)
        .authorities(new HashSet<>(request.getAuthorities()))
        // Set the expiration date to one year from now
        .expiresAt(LocalDateTime.now().plusYears(1))
        .build();

    // todo - sm - this should be handled in the entity base class
    apiUser.setCreatedDate(OffsetDateTime.now());
    apiUser.setUpdatedDate(OffsetDateTime.now());

    // Save the new API user record in the database
    apiUserRepository.save(apiUser);

    // Prepare the response (the plain API key should be communicated securely)
    return ApiUserRegistrationResponse.builder()
        .ownerName(apiUser.getOwnerName())
        .email(apiUser.getEmail())
        .apiKey(plainApiKey)
        .build();
  }
}
