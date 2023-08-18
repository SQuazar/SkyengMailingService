package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.MailingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingHistoryRepository extends JpaRepository<MailingHistory, Integer> {
}
