package ru.ardyc.kairos.service.notification

import org.springframework.stereotype.Service
import ru.ardyc.kairos.repository.MeetingMemberRepository
import ru.ardyc.kairos.repository.MeetingRepository
import ru.ardyc.kairos.service.hoshi.HoshiNotificationSender
import java.time.OffsetDateTime
import java.time.ZoneId

@Service
class NotificationService(
    private val meetingRepository: MeetingRepository,
    private val meetingUserRepository: MeetingMemberRepository,
    private val hoshiNotificationSender: HoshiNotificationSender
) {

    fun sendNotifications() {
        meetingRepository.findAllByStartBetween(
            OffsetDateTime.now(ZoneId.of("Europe/Moscow")),
            OffsetDateTime.now(ZoneId.of("Europe/Moscow")).plusMinutes(5)
        )
            .forEach { meeting ->
                meetingUserRepository.getMeetingMembersByMeetingId(meeting.uuid)
                    .forEach { it ->
                        hoshiNotificationSender.send(
                            it.userId,
                            meeting.title,
                            "Привет, это собрание начнется в течении 5 минут: ${meeting.start}"
                        )
                    }
            }
    }

}