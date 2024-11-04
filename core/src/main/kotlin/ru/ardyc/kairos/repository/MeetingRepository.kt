package ru.ardyc.kairos.repository

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.ardyc.kairos.model.entity.Meeting
import java.time.OffsetDateTime
import java.util.UUID

@Repository
interface MeetingRepository : CrudRepository<Meeting, UUID> {

    @Query("SELECT * FROM meeting WHERE start >= :startAfter AND start <= :endBefore")
    fun findAllByStartBetween(startAfter: OffsetDateTime, endBefore: OffsetDateTime): List<Meeting>
}