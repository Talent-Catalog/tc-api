package org.tctalent.anonymization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tctalent.anonymization.domain.entity.ApiUser;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {
}
