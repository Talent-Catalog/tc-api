package org.tctalent.anonymization.domain.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.tctalent.anonymization.domain.entity.converter.AuthoritiesConverter;

/**
 * Represents a user of the API. The ApiUser entity is used to store information about the user,
 * such as their name, email, API key, authorities, and expiration date.
 *
 * @author sadatmalik
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "api_user")
@SequenceGenerator(name = "seq_gen", sequenceName = "api_user_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class ApiUser extends AbstractDomainEntity<Long> {
  private String ownerName;
  private String email;
  private String apiKeyHash;

  @Convert(converter = AuthoritiesConverter.class)
  private Set<String> authorities = new HashSet<>();

  private LocalDateTime expiresAt;
}
