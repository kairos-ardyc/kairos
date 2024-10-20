package ru.ardyc.kairos.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.ardyc.kairos.model.entity.Meeting
import java.util.UUID

@Repository
interface MeetingRepository : CrudRepository<Meeting, UUID>
