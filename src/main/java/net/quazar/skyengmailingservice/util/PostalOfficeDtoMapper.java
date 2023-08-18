package net.quazar.skyengmailingservice.util;

import net.quazar.skyengmailingservice.entity.PostalOffice;
import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostalOfficeDtoMapper {
    PostalOfficeDtoMapper INSTANCE = Mappers.getMapper(PostalOfficeDtoMapper.class);

    PostalOfficeDto postalOfficeToDto(PostalOffice postalOffice);
}
