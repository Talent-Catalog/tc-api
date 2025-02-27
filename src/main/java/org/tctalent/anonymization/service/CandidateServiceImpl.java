package org.tctalent.anonymization.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.tctalent.anonymization.domain.document.CandidateDocument;
import org.tctalent.anonymization.exception.NoSuchObjectException;
import org.tctalent.anonymization.mapper.DocumentMapper;
import org.tctalent.anonymization.model.Candidate;
import org.tctalent.anonymization.model.CandidatePage;
import org.tctalent.anonymization.repository.CandidateDocumentRepository;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

  private final CandidateDocumentRepository candidateDocumentRepository;
  private final MongoTemplate mongoTemplate;
  private final DocumentMapper documentMapper;

  @Override
  public CandidatePage findAll(Pageable pageable, List<String> locations,
      List<String> nationalities, List<String> occupations) {

    Map<String, List<String>> filters = new HashMap<>();
    if (locations != null) {
      //TODO JC Fix key
      filters.put("location", locations);
    }
    if (nationalities != null) {
      //TODO JC Fix key
      filters.put("nationality", nationalities);
    }
    if (occupations != null) {
      filters.put("candidateOccupations.occupation.isco08Code", occupations);
    }

    //Construct the basic query from all the filters
    Query query = new Query();
    filters.forEach((key, values) -> query
        .addCriteria(Criteria.where(key).in(values)));

    //Run the query requesting just the page required
    List<Candidate> candidates = mongoTemplate.find(query.with(pageable), CandidateDocument.class)
            .stream()
            .map(documentMapper::toCandidateModel)
            .toList();
    //Count the total number of candidates (ie without paging limits).
    long count = mongoTemplate.count(query, CandidateDocument.class);

    //Construct a page
    Page<Candidate> candidatePage = new PageImpl<>(candidates, pageable, count);

    //Convert to our CandidatePage structure
    return documentMapper.toCandidateModelPage(candidatePage);
  }

  @Override
  public Candidate findByPublicId(String publicId) {
    return candidateDocumentRepository
        .findByPublicId(publicId)
        .map(documentMapper::toCandidateModel)
        .orElseThrow(() -> new NoSuchObjectException(Candidate.class, publicId));
  }

}
