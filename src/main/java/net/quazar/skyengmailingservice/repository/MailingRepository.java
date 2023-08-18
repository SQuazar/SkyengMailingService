package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingRepository extends JpaRepository<Mailing, Integer> {
}
