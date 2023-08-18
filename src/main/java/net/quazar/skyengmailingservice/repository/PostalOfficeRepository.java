package net.quazar.skyengmailingservice.repository;

import net.quazar.skyengmailingservice.entity.PostalOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalOfficeRepository extends JpaRepository<PostalOffice, Integer> {
}
