package ru.ardyc.kairos.job

import org.jobrunr.jobs.annotations.Recurring
import org.springframework.stereotype.Service
import ru.ardyc.kairos.service.notification.NotificationService

@Service
class MeetingNotificationJob(
    private val notificationService: NotificationService
) {

    @Recurring(
        id = "meeting-notification",
        cron = "*/5 * * * *"
    )
    fun sendNotifications() {
        println("Sending notifications")
        notificationService.sendNotifications()
    }

}