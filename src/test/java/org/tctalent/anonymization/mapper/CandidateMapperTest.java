package org.tctalent.anonymization.mapper;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.model.IdentifiableCandidate;

@SpringBootTest
public class CandidateMapperTest {

  @Autowired
  @Qualifier("candidateMapperImpl")
  private CandidateMapper mapper;

  @Test
  void shouldMapContactConsentPartnersToContactConsentTcPartners() {
    // Create an IdentifiableCandidate with test data
    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setContactConsentPartners(true);

    // Map the source object to a CandidateDocument
    CandidateDocument result = mapper.anonymize(source);

    // Verify that the field is correctly mapped
    assertThat(result.getContactConsentTcPartners())
        .isNotNull()
        .isEqualTo(true);
    }

  @Test
  void shouldHandleNullContactConsentPartners() {
    // Create an IdentifiableCandidate with null data
    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setContactConsentPartners(null);

    // Map the source object to a CandidateDocument
    CandidateDocument result = mapper.anonymize(source);

    // Verify the mapping works for null input
    assertThat(result.getContactConsentTcPartners()).isNull();
  }

}
