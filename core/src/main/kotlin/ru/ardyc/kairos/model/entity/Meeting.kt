package ru.ardyc.kairos.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import java.util.UUID

@Table("meeting")
data class Meeting(
    @Id
    val uuid: UUID,
    val owner: UUID,
    val title: String,
    val room: String,
    val description: String,
    val start: Instant,
    val end: Instant,
) : Persistable<UUID> {
    override fun getId() = uuid

    override fun isNew() = true
}
