package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailingHistoryRepository extends JpaRepository<MailingHistory, Integer> {
    List<MailingHistory> findAllByMailing(Mailing mailing);
}
