package org.tctalent.anonymization.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.mapper.CandidateMapper;
import org.tctalent.anonymization.model.IdentifiableCandidatePage;
import org.tctalent.anonymization.repository.CandidateMongoRepository;

@SpringBootTest
class TalentCatalogServiceImplTest {
  @Autowired
  TalentCatalogServiceImpl tcService;

  @Autowired
  CandidateMongoRepository anonCandidateRepository;

  @Autowired
  CandidateMapper candidateMapper;

  @BeforeEach
  void setUp() {
  }

  @Test
  void login() {
    try {
      tcService.login();
      assertTrue(tcService.isLoggedIn());
    } catch (RestClientException ex) {
      fail(ex);
    }
  }

  @Test
  void fetchPageOfCandidateDataAsJson() {
    try {
      String pageOfDataAsJson = tcService.fetchPageOfCandidateDataAsJson(0);
      assertNotNull(pageOfDataAsJson);
    } catch (RestClientException ex) {
      fail(ex);
    }
  }

  @Test
  @Transactional
  void loginAndFetchPageOfCandidateData() {
    try {
      tcService.login();
      assertTrue(tcService.isLoggedIn());

      IdentifiableCandidatePage pageOfIdentifiableCandidates = tcService
          .fetchPageOfIdentifiableCandidateData(0, 100);
      assertNotNull(pageOfIdentifiableCandidates);

      List<CandidateDocument> anonCandidates = pageOfIdentifiableCandidates
          .getContent()
          .stream()
          .map(candidateMapper::anonymize)
          .toList();

      assertNotNull(anonCandidates);
      String collect = anonCandidates
          .stream()
          .map(ca -> ca.getPublicId().toString())
          .collect(Collectors.joining(","));
      System.out.println("Received numbers: " + collect);

      anonCandidateRepository.saveAll(anonCandidates);
      for (CandidateDocument candidate : anonCandidateRepository.findAll()) {
        System.out.println(candidate);
      }

    } catch (RestClientException ex) {
      fail(ex);
    }
  }

  @Test
  @Transactional
  void loginAndFetchFirstFewPagesOfCandidateData() {
    try {
      tcService.login();
      assertTrue(tcService.isLoggedIn());

      for (int i = 0; i < 3; i++) {
        IdentifiableCandidatePage pageOfIdentifiableCandidates = tcService
            .fetchPageOfIdentifiableCandidateData(i, 100);
        assertNotNull(pageOfIdentifiableCandidates);

        List<CandidateDocument> anonCandidates = pageOfIdentifiableCandidates
            .getContent()
            .stream()
            .map(candidateMapper::anonymize)
            .toList();

        assertNotNull(anonCandidates);
        String collect = anonCandidates
            .stream()
            .map(ca -> ca.getPublicId().toString())
            .collect(Collectors.joining(","));
        System.out.println("Received numbers: " + collect);

        anonCandidateRepository.saveAll(anonCandidates);
        for (CandidateDocument candidate : anonCandidateRepository.findAll()) {
          System.out.println(candidate);
        }

        Thread.sleep(3000);
      }

    } catch (Exception ex) {
      fail(ex);
    }
  }

}
