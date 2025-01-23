package org.tctalent.anonymization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.tctalent.anonymization.config.JacksonConfig;
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

        service = new AnonymizationServiceImpl(objectMapper);
    }

    @Test
    void anonymize() throws JsonProcessingException {

        UUID uuid = UUID.randomUUID();
        String id = "123456";

        Country nationality = Country.builder()
            .isoCode("GB")
            .name("United Kingdon")
            .status(Status.active)
            .build();

        IdentifiableCandidate identifiableCandidate = IdentifiableCandidate.builder()
            .id(id)
            .uuid(uuid)
            .phone("+1-234-567-890")
            .address1("123 Main St, Springfield, IL")
            .dob(LocalDate.parse("1985-06-15"))
            .nationality(nationality)
            .createdDate(OffsetDateTime.now())
            .build();

        Candidate candidate = service.anonymize(identifiableCandidate);

        assertEquals(uuid, candidate.getUuid());
        System.out.println(candidate);
    }
}
