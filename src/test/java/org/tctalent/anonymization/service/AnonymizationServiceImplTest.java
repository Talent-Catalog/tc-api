package org.tctalent.anonymization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.tctalent.anonymization.config.JacksonConfig;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.mapper.EntityMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.Country;
import org.tctalent.anonymization.model.IdentifiableCandidate;
import org.tctalent.anonymization.model.Status;

/**
 * Test
 *
 * @author John Cameron
 */
class AnonymizationServiceImplTest {
    AnonymizationService service;

    @BeforeEach
    void setUp() {
        JacksonConfig jacksonConfig = new JacksonConfig();
        ObjectMapper objectMapper = jacksonConfig.objectMapper();

        // Provide dummy mocks for the unused dependencies
        EntityMapper entityMapper = Mockito.mock(EntityMapper.class);
        DocumentMapper documentMapper = Mockito.mock(DocumentMapper.class);

        service = new AnonymizationServiceImpl(objectMapper, entityMapper, documentMapper);
    }

    @Test
    void anonymize() throws JsonProcessingException {

        String publicId = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        String id = "123456";

        Country nationality = Country.builder()
            .isoCode("GB")
            .name("United Kingdon")
            .status(Status.ACTIVE)
            .build();

        IdentifiableCandidate identifiableCandidate = IdentifiableCandidate.builder()
            .id(id)
            .publicId(publicId)
            .phone("+1-234-567-890")
            .address1("123 Main St, Springfield, IL")
            .dob(LocalDate.parse("1985-06-15"))
            .nationality(nationality)
            .createdDate(OffsetDateTime.now())
            .build();

        Candidate candidate = service.anonymizeToDto(identifiableCandidate);

        assertEquals(publicId, candidate.getPublicId());
        System.out.println(candidate);
    }
}
