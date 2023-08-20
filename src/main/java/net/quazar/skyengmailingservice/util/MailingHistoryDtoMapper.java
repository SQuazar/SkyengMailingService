package net.quazar.skyengmailingservice.util;

import net.quazar.skyengmailingservice.entity.MailingHistory;
import net.quazar.skyengmailingservice.entity.dto.MailingHistoryNodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailingHistoryDtoMapper {
    MailingHistoryDtoMapper INSTANCE = Mappers.getMapper(MailingHistoryDtoMapper.class);

    @Mapping(target = "mailingId", source = "mailing.id")
    @Mapping(target = "postalOfficeIndex", source = "postalOffice.index")
    MailingHistoryNodeDto mailingHistoryToDto(MailingHistory mailingHistory);
}
