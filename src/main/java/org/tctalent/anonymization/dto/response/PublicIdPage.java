package org.tctalent.anonymization.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicIdPage {
  private List<PublicIdDto> content;
  private Integer totalElements;
  private Integer totalPages;
  private Integer size;
  private Integer number;
  private Integer numberOfElements;
}
