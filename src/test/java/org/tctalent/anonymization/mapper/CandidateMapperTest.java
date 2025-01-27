package org.tctalent.anonymization.mapper;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.tctalent.anonymization.entity.mongo.CandidateDocument;
import org.tctalent.anonymization.entity.mongo.Dependant;
import org.tctalent.anonymization.model.DependantRelations;
import org.tctalent.anonymization.model.Gender;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.model.IdentifiableDependant;
import org.tctalent.anonymization.model.IdentifiablePartner;
import org.tctalent.anonymization.model.Registration;
import org.tctalent.anonymization.model.YesNo;

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

  @Test
  void shouldMapPartnerCandidatePublicIdToPartnerPublicId() {
    // Arrange
    UUID partnerPublicId = UUID.randomUUID();
    IdentifiableCandidate source = new IdentifiableCandidate();
    IdentifiablePartner partnerCandidate = new IdentifiablePartner();
    partnerCandidate.setPublicId(partnerPublicId);
    source.setPartnerCandidate(partnerCandidate);

    // Act
    CandidateDocument result = mapper.anonymize(source);

    // Assert
    assertThat(result.getPartnerPublicId())
        .isNotNull()
        .isEqualTo(partnerPublicId);
  }

  @Test
  void shouldMapDobToYearOfBirth() {
    // Arrange
    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setDob(LocalDate.of(1985, 6, 15));

    // Act
    CandidateDocument result = mapper.anonymize(source);

    // Assert
    assertThat(result.getYearOfBirth())
        .isNotNull()
        .isEqualTo(1985);
  }

  @Test
  void shouldHandleNullDob() {
    // Arrange
    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setDob(null);

    // Act
    CandidateDocument result = mapper.anonymize(source);

    // Assert
    assertThat(result.getYearOfBirth()).isNull();
  }

  @Test
  void shouldMapCandidateDependantsIncludingYearOfBirth() {
    // Arrange
    IdentifiableDependant dependant1 = new IdentifiableDependant();
    dependant1.setDob(LocalDate.of(2005, 3, 10));
    dependant1.setRelation(DependantRelations.Child);
    dependant1.setGender(Gender.male);
    dependant1.setRegistered(Registration.UNHCR);
    dependant1.setHealthConcern(YesNo.No);

    IdentifiableDependant dependant2 = new IdentifiableDependant();
    dependant2.setDob(LocalDate.of(2010, 7, 20));
    dependant2.setRelation(DependantRelations.Child);
    dependant2.setGender(Gender.female);
    dependant2.setRegistered(Registration.NA);
    dependant2.setHealthConcern(YesNo.Yes);

    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setCandidateDependants(List.of(dependant1, dependant2));

    // Act
    CandidateDocument result = mapper.anonymize(source);

    // Assert
    assertThat(result.getCandidateDependants()).hasSize(2);

    Dependant mappedDependant1 = result.getCandidateDependants().get(0);
    assertThat(mappedDependant1.getYearOfBirth()).isEqualTo(2005);
    assertThat(mappedDependant1.getRelation().name()).isSameAs(DependantRelations.Child.name());
    assertThat(mappedDependant1.getGender().name()).isEqualTo(Gender.male.name());
    assertThat(mappedDependant1.getRegistered().name()).isEqualTo(Registration.UNHCR.name());
    assertThat(mappedDependant1.getHealthConcern().name()).isEqualTo(YesNo.No.name());

    Dependant mappedDependant2 = result.getCandidateDependants().get(1);
    assertThat(mappedDependant2.getYearOfBirth()).isEqualTo(2010);
    assertThat(mappedDependant2.getRelation().name()).isEqualTo(DependantRelations.Child.name());
    assertThat(mappedDependant2.getGender().name()).isEqualTo(Gender.female.name());
    assertThat(mappedDependant2.getRegistered().name()).isEqualTo(Registration.NA.name());
    assertThat(mappedDependant2.getHealthConcern().name()).isEqualTo(YesNo.Yes.name());
  }

  @Test
  void shouldHandleNullCandidateDependants() {
    // Arrange
    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setCandidateDependants(null);

    // Act
    CandidateDocument result = mapper.anonymize(source);

    // Assert
    assertThat(result.getCandidateDependants()).isNull();
  }

  @Test
  void shouldHandleNullDobInDependants() {
    // Arrange
    IdentifiableDependant dependant = new IdentifiableDependant();
    dependant.setDob(null);
    dependant.setRelation(DependantRelations.Child);

    IdentifiableCandidate source = new IdentifiableCandidate();
    source.setCandidateDependants(List.of(dependant));

    // Act
    CandidateDocument result = mapper.anonymize(source);

    // Assert
    assertThat(result.getCandidateDependants()).hasSize(1);
    Dependant mappedDependant = result.getCandidateDependants().get(0);
    assertThat(mappedDependant.getYearOfBirth()).isNull();
    assertThat(mappedDependant.getRelation().name()).isEqualTo(DependantRelations.Child.name());
  }

}
