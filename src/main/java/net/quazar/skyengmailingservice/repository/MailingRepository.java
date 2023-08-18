package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRepository extends JpaRepository<Mailing, Integer> {
}
