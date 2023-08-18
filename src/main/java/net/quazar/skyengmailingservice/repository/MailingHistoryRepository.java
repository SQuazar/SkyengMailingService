package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.MailingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingHistoryRepository extends JpaRepository<MailingHistory, Integer> {
}
