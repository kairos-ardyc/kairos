package ru.ardyc.kairos.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("meeting")
data class Meeting(
    @Id
    var uuid: UUID,
    var owner: UUID,
    var title: String,
    var room: String,
    var description: String,
    var start: Instant,
    var end: Instant,
) : Persistable<UUID> {
    override fun getId() = uuid
    override fun isNew() = true
}
