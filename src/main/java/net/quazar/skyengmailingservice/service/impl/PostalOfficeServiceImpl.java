package net.quazar.skyengmailingservice.service.impl;

import lombok.AllArgsConstructor;
import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingHistory;
import net.quazar.skyengmailingservice.entity.MailingStatus;
import net.quazar.skyengmailingservice.entity.PostalOffice;
import net.quazar.skyengmailingservice.entity.dto.PostalOfficeDto;
import net.quazar.skyengmailingservice.exception.MailingNotFoundException;
import net.quazar.skyengmailingservice.exception.PostalOfficeAlreadyExistsException;
import net.quazar.skyengmailingservice.exception.PostalOfficeNotFoundException;
import net.quazar.skyengmailingservice.repository.MailingHistoryRepository;
import net.quazar.skyengmailingservice.repository.MailingRepository;
import net.quazar.skyengmailingservice.repository.MailingStatusRepository;
import net.quazar.skyengmailingservice.repository.PostalOfficeRepository;
import net.quazar.skyengmailingservice.service.PostalOfficeService;
import net.quazar.skyengmailingservice.util.PostalOfficeDtoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostalOfficeServiceImpl implements PostalOfficeService {
    private final PostalOfficeRepository postalOfficeRepository;
    private final MailingRepository mailingRepository;
    private final MailingStatusRepository mailingStatusRepository;
    private final MailingHistoryRepository mailingHistoryRepository;

    private final PostalOfficeDtoMapper postalOfficeMapper = PostalOfficeDtoMapper.INSTANCE;

    @Override
    public Set<PostalOfficeDto> findAll() {
        return postalOfficeRepository.findAll()
                .stream()
                .map(postalOfficeMapper::postalOfficeToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public PostalOfficeDto findByIndex(String index) {
        PostalOffice postalOffice = postalOfficeRepository.findById(index)
                .orElseThrow(() -> new PostalOfficeNotFoundException(String.format("Почтовое отделение по индексу %s не найдено", index)));
        return postalOfficeMapper.postalOfficeToDto(postalOffice);
    }

    @Override
    public PostalOfficeDto createPostalOffice(String index, String address, String name) {
        if (postalOfficeRepository.findById(index).isPresent())
            throw new PostalOfficeAlreadyExistsException(String.format("Почтовое отделение по индексу %s уже зарегистрировано", index));
        return postalOfficeMapper.postalOfficeToDto(
                postalOfficeRepository.save(PostalOffice.builder()
                        .index(index)
                        .address(address)
                        .name(name)
                        .build())
        );
    }

    @Override
    public void registerIncomingMailing(String postalOfficeIndex, int mailingId) {
        PostalOffice postalOffice = postalOfficeRepository.findById(postalOfficeIndex)
                .orElseThrow(() -> new PostalOfficeNotFoundException(String.format("Почтовое отделение по индексу %s не найдено", postalOfficeIndex)));
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException(String.format("Почтовое отправление %d не найдено", mailingId)));

        mailingStatusRepository.save(MailingStatus.builder()
                .mailing(mailing)
                .status(MailingStatus.Status.ROUTING)
                .build());
        MailingHistory mailingHistory = MailingHistory.builder()
                .mailing(mailing)
                .postalOffice(postalOffice)
                .date(LocalDateTime.now())
                .operation(String.format("Прибыло в почтовое отделение %s %s \"%s\"",
                        postalOffice.getIndex(),
                        postalOffice.getAddress(),
                        postalOffice.getName()))
                .build();
        mailingHistoryRepository.save(mailingHistory);
    }

    @Override
    public void registerOutgoingMailing(String postalOfficeIndex, int mailingId) {
        PostalOffice postalOffice = postalOfficeRepository.findById(postalOfficeIndex)
                .orElseThrow(() -> new PostalOfficeNotFoundException(String.format("Почтовое отделение по индексу %s не найдено", postalOfficeIndex)));
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException(String.format("Почтовое отправление %d не найдено", mailingId)));

        mailingStatusRepository.save(MailingStatus.builder()
                .mailing(mailing)
                .status(MailingStatus.Status.ROUTING)
                .build());
        MailingHistory mailingHistory = MailingHistory.builder()
                .mailing(mailing)
                .postalOffice(postalOffice)
                .date(LocalDateTime.now())
                .operation(String.format("Отправлено из почтового отделения %s %s \"%s\"",
                        postalOffice.getIndex(),
                        postalOffice.getAddress(),
                        postalOffice.getName()))
                .build();
        mailingHistoryRepository.save(mailingHistory);
    }
}
