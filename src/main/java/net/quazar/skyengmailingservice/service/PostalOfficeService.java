package net.quazar.skyengmailingservice.service;

import net.quazar.skyengmailingservice.entity.PostalOffice;
import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;

import java.util.Set;

public interface PostalOfficeService {
    Set<PostalOfficeDto> findAll();

    PostalOfficeDto createPostalOffice(String index, String address, String name);

    void registerIncomingMailing(String postalOfficeIndex, int mailingId);

    void registerOutgoingMailing(String postalOfficeIndex, int mailingId);
}
