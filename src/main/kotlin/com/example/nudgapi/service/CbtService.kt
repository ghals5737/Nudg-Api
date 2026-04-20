package com.example.nudgapi.service

import com.example.nudgapi.domain.CbtEntry
import com.example.nudgapi.domain.CbtEntryTag
import com.example.nudgapi.dto.*
import com.example.nudgapi.exception.ForbiddenException
import com.example.nudgapi.exception.NotFoundException
import com.example.nudgapi.repository.CbtEntryRepository
import com.example.nudgapi.repository.CbtEntryTagRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CbtService(
    private val cbtEntryRepository: CbtEntryRepository,
    private val cbtEntryTagRepository: CbtEntryTagRepository,
) {

    @Transactional(readOnly = true)
    fun list(userId: Long, page: Int, limit: Int): PagedResponse<CbtEntryResponse> {
        val result = cbtEntryRepository.findAllByUserId(
            userId, PageRequest.of(page - 1, limit, Sort.by("createdAt").descending())
        )
        return PagedResponse(
            data = result.content.map { e -> CbtEntryResponse.from(e, tagsOf(e.id!!)) },
            total = result.totalElements, page = page, limit = limit,
        )
    }

    @Transactional
    fun create(userId: Long, req: CreateCbtEntryRequest): CbtEntryResponse {
        val entry = cbtEntryRepository.save(
            CbtEntry(
                userId = userId, emoji = req.emoji, mood = req.mood,
                moodBg = req.moodBg ?: "#cce8e4", moodText = req.moodText ?: "#3d5653", content = req.content,
            )
        )
        val tags = req.tags?.map { cbtEntryTagRepository.save(CbtEntryTag(entryId = entry.id!!, tag = it)) } ?: emptyList()
        return CbtEntryResponse.from(entry, tags.map { it.tag })
    }

    @Transactional(readOnly = true)
    fun get(userId: Long, entryId: Long): CbtEntryResponse {
        val e = findOwned(userId, entryId)
        return CbtEntryResponse.from(e, tagsOf(entryId))
    }

    @Transactional
    fun update(userId: Long, entryId: Long, req: UpdateCbtEntryRequest): CbtEntryResponse {
        val e = findOwned(userId, entryId)
        req.emoji?.let { e.emoji = it }
        req.mood?.let { e.mood = it }
        req.moodBg?.let { e.moodBg = it }
        req.moodText?.let { e.moodText = it }
        req.content?.let { e.content = it }
        cbtEntryRepository.save(e)
        val tags = if (req.tags != null) {
            cbtEntryTagRepository.deleteAllByEntryId(entryId)
            req.tags.map { cbtEntryTagRepository.save(CbtEntryTag(entryId = entryId, tag = it)) }.map { it.tag }
        } else tagsOf(entryId)
        return CbtEntryResponse.from(e, tags)
    }

    @Transactional
    fun delete(userId: Long, entryId: Long) {
        findOwned(userId, entryId)
        cbtEntryTagRepository.deleteAllByEntryId(entryId)
        cbtEntryRepository.deleteById(entryId)
    }

    private fun findOwned(userId: Long, entryId: Long): CbtEntry {
        val e = cbtEntryRepository.findById(entryId).orElseThrow { NotFoundException("Entry not found") }
        if (e.userId != userId) throw ForbiddenException("Access denied")
        return e
    }

    private fun tagsOf(entryId: Long): List<String> =
        cbtEntryTagRepository.findAllByEntryId(entryId).map { it.tag }
}
