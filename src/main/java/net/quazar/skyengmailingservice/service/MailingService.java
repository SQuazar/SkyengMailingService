package net.quazar.skyengmailingservice.service;

import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingStatus;
import net.quazar.skyengmailingservice.entity.dto.MailingDto;
import net.quazar.skyengmailingservice.entity.dto.MailingHistoryNodeDto;

import java.util.List;

public interface MailingService {
    MailingDto getMailingById(int mailingId);

    MailingDto registerNewMailing(Mailing.Type type, String receiverIndex, String receiverAddress, String receiverName);

    void changeMailingStatus(int mailingId, MailingStatus.Status status);

    List<MailingHistoryNodeDto> getMailingHistory(int mailingId);
}
