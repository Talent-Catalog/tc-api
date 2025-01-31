package org.tctalent.anonymization.domain.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.tctalent.anonymization.entity.db.AbstractDomainObject;

@Getter
@Setter
@Entity
@Table(name = "anonymous_candidate")
@SequenceGenerator(name = "seq_gen", sequenceName = "anonymous_candidate_id_seq", allocationSize = 1)
@Slf4j
public class AnonymousCandidate extends AbstractDomainObject<Long> {

  @Column(nullable = false, unique = true)
  private String publicId;

  @Column(name = "created_date")
  private OffsetDateTime createdDate;

  @Column(name = "updated_date")
  private OffsetDateTime updatedDate;

}
