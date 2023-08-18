package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.MailingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingStatusRepository extends JpaRepository<MailingStatus, Integer> {
}
