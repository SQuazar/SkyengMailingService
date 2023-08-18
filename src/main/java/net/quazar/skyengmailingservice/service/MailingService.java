package net.quazar.skyengmailingservice.service;

import net.quazar.skyengmailingservice.entity.Mailing;
import net.quazar.skyengmailingservice.entity.MailingStatus;
import net.quazar.skyengmailingservice.entity.dto.MailingDto;
import net.quazar.skyengmailingservice.entity.dto.MailingHistoryNodeDto;

import java.util.Set;

public interface MailingService {
    MailingDto registerNewMailing(Mailing.Type type, String receiverIndex, String receiverAddress, String receiverName);

    void changeMailingStatus(int mailingId, MailingStatus.Status status);

    String getMailingStatus(int mailingId);

    Set<MailingHistoryNodeDto> getMailingHistory(int mailingId);
}
