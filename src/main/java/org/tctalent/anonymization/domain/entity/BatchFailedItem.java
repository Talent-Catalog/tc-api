package org.tctalent.anonymization.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "batch_failed_items")
@SequenceGenerator(name = "seq_gen", sequenceName = "candidate_certification_id_seq", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
public class BatchFailedItem extends AbstractAuditableDomainEntity<Long>{

  @Column(name = "job_execution_id", nullable = false)
  private Long jobExecutionId;

  @Column(name = "step_execution_id", nullable = false)
  private Long stepExecutionId;

  @Column(name = "step_name", length = 100)
  private String stepName;

  @Column(name = "item_type", length = 50)
  private String itemType;

  @Column(name = "item_public_id", length = 100)
  private String itemPublicId;

  @Column(name = "error_phase", length = 20)
  private String errorPhase;

  @Column(name = "error_message")
  private String errorMessage;

}
