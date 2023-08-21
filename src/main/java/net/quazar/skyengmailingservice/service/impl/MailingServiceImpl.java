package net.quazar.skyengmailingservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingHistory;
import net.quazar.skyengmailingservice.entity.MailingStatus;
import net.quazar.skyengmailingservice.entity.dto.MailingDto;
import net.quazar.skyengmailingservice.entity.dto.MailingHistoryNodeDto;
import net.quazar.skyengmailingservice.exception.MailingNotFoundException;
import net.quazar.skyengmailingservice.repository.MailingHistoryRepository;
import net.quazar.skyengmailingservice.repository.MailingRepository;
import net.quazar.skyengmailingservice.repository.MailingStatusRepository;
import net.quazar.skyengmailingservice.service.MailingService;
import net.quazar.skyengmailingservice.util.MailingDtoMapper;
import net.quazar.skyengmailingservice.util.MailingHistoryDtoMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MailingServiceImpl implements MailingService {
    private final MailingRepository mailingRepository;
    private final MailingHistoryRepository mailingHistoryRepository;
    private final MailingStatusRepository mailingStatusRepository;

    private final MailingDtoMapper mailingMapper = MailingDtoMapper.INSTANCE;
    private final MailingHistoryDtoMapper historyMapper = MailingHistoryDtoMapper.INSTANCE;

    @Override
    public MailingDto getMailingById(int mailingId) {
        MailingDto dto = mailingRepository.findById(mailingId)
                .map(mailingMapper::mailingToDto)
                .orElseThrow(() -> new MailingNotFoundException("Почтовое отправление не найдено"));

        MailingStatus.Status status = mailingStatusRepository.findByMailingId(mailingId)
                .map(MailingStatus::getStatus)
                .orElseThrow(() -> new MailingNotFoundException("Не удалось определить статус почтового отправления"));
        dto.setStatus(status.getLocalized());

        return dto;
    }

    @Transactional
    @Override
    public MailingDto registerNewMailing(Mailing.Type type, String receiverIndex, String receiverAddress, String receiverName) {
        Mailing mailing = Mailing.builder()
                .type(type)
                .receiverIndex(receiverIndex)
                .receiverAddress(receiverAddress)
                .receiverName(receiverName)
                .build();
        MailingDto dto = mailingMapper.mailingToDto(
                mailingRepository.save(mailing)
        );

        MailingStatus.Status status = mailingStatusRepository.save(MailingStatus.builder()
                .mailing(mailing)
                .status(MailingStatus.Status.REGISTERED)
                .build()).getStatus();
        mailingHistoryRepository.save(MailingHistory.builder()
                .mailing(mailing)
                .date(LocalDateTime.now())
                .operation("Зарегистрировано новое почтовое отправление")
                .build());

        dto.setStatus(status.getLocalized());
        return dto;
    }

    @Override
    public void changeMailingStatus(int mailingId, MailingStatus.Status status) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Почтовое отправление не найдено"));
        MailingStatus mailingStatus = mailingStatusRepository.findByMailingId(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Запись о статусе почтового отправления не найдена"));
        mailingStatus.setStatus(status);

        mailingHistoryRepository.save(MailingHistory.builder()
                .date(LocalDateTime.now())
                .operation(String.format("Статус почтового отправления изменён на \"%s\"", status.getLocalized()))
                .mailing(mailing)
                .build());

        mailingStatusRepository.save(mailingStatus);
    }

    @Override
    public List<MailingHistoryNodeDto> getMailingHistory(int mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Почтовое отправление не найдено"));
        return mailingHistoryRepository.findAllByMailing(mailing, Sort.by(Sort.Direction.DESC, "date"))
                .stream()
                .map(historyMapper::mailingHistoryToDto)
                .collect(Collectors.toList());
    }
}
