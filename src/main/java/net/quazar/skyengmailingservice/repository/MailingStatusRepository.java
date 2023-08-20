package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.MailingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailingStatusRepository extends JpaRepository<MailingStatus, Integer> {
    Optional<MailingStatus> findByMailingId(int mailingId);
}
