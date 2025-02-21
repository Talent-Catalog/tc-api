package org.tctalent.anonymization.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.tctalent.anonymization.dto.request.ApiUserRegistrationRequest;
import org.tctalent.anonymization.dto.response.ApiUserRegistrationResponse;
import org.tctalent.anonymization.logging.LogBuilder;
import org.tctalent.anonymization.service.ApiUserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/api-users")
public class AdminApiUserController {

  private final ApiUserService apiUserService;

  /**
   * Registers a new API user.
   * Only administrators can register new API users.
   *
   * @param request the API user registration request
   * @return the API user registration response with the generated API key
   */
  @PostMapping
//  @PreAuthorize("hasAuthority('ADMIN')") // todo - sm - admin role - pick this up in a separate story
  public ResponseEntity<ApiUserRegistrationResponse> registerApiUser(
      @Valid @RequestBody ApiUserRegistrationRequest request) {

    LogBuilder.builder(log).action("registerApiUser")
        .message("Registering a new API user: " + request)
        .logInfo();

    ApiUserRegistrationResponse response = apiUserService.registerApiUser(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
