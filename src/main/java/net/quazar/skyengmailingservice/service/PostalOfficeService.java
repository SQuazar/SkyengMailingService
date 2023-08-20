package net.quazar.skyengmailingservice.service;

import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;

public interface PostalOfficeService {
    PostalOfficeDto createPostalOffice(String index, String address, String name);

    void registerIncomingMailing(String postalOfficeIndex, int mailingId);

    void registerOutgoingMailing(String postalOfficeIndex, int mailingId);
}
