package com.grdkrll.kfinance.repository

import com.grdkrll.kfinance.model.database.GroupsDatabase
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.model.table.GroupEntity
import com.grdkrll.kfinance.service.groups.GroupsService
import io.ktor.client.call.*
import org.koin.core.component.getScopeId

class GroupRepository(
    private val groupsService: GroupsService,
    private val tokenRepository: TokenRepository,
    private val database: GroupsDatabase
) {
    suspend fun createGroup(name: String, handle: String, password: String): Result<GroupResponse> {
        return try {
            val res = groupsService.createGroup(
                name, handle, password, tokenRepository.fetchAuthToken()
            ).body<GroupResponse>()
            database.getGroupsDao().addGroup(
                GroupEntity(
                    res.id,
                    res.name,
                    res.handle
                )
            )
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun addMember(handle: String, password: String): Result<GroupResponse> {
        return try {
            val res = groupsService.addMember(handle, password, tokenRepository.fetchAuthToken())
                .body<GroupResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun removeMember(handle: String, password: String): Result<GroupResponse> {
        return try {
            val res = groupsService.removeMember(handle, password, tokenRepository.getScopeId())
                .body<GroupResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getAllGroupsOfUser(): Result<List<GroupResponse>> {
        return try {
            val res = groupsService.getAllGroupsOfUser(tokenRepository.fetchAuthToken()).body<List<GroupResponse>>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}