package org.tctalent.anonymization.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.tctalent.anonymization.service.AnonymousCandidateService;

@Slf4j
@Component
@RequiredArgsConstructor
public class AnonymousCandidateBootstrapper implements CommandLineRunner {

  private final AnonymousCandidateService anonymousCandidateService;

  @Override
  public void run(String... args) {
    anonymousCandidateService.saveAnonymousCandidate(); // Calls the transactional method
  }

}
