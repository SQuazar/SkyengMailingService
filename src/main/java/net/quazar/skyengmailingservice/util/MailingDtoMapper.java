package net.quazar.skyengmailingservice.util;

import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.dto.MailingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailingDtoMapper {
    MailingDtoMapper INSTANCE = Mappers.getMapper(MailingDtoMapper.class);

    @Mapping(target = "type", source = "type.localized")
    MailingDto mailingToDto(Mailing mailing);
}
