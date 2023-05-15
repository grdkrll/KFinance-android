package com.grdkrll.kfinance.repository

import com.grdkrll.kfinance.model.database.GroupsDatabase
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.model.dto.groups.response.MemberResponse
import com.grdkrll.kfinance.model.table.GroupEntity
import com.grdkrll.kfinance.service.GroupsService
import io.ktor.client.call.*
import org.koin.core.component.getScopeId

class GroupRepository(
    private val groupsService: GroupsService,
    private val tokenRepository: TokenRepository,
    private val database: GroupsDatabase,
    private val selectedGroupRepository: SelectedGroupRepository
) {
    fun selectGroup(id: Int, name: String) = selectedGroupRepository.saveGroup(id, name)

    fun deselectGroup() = selectedGroupRepository.deselectGroup()
    fun fetchGroup() = selectedGroupRepository.fetchGroup()

    suspend fun createGroup(name: String, handle: String, password: String): Result<GroupResponse> {
        return try {
            val res = groupsService.createGroup(
                name, handle, password, tokenRepository.fetchAuthToken()
            ).body<GroupResponse>()
            database.getGroupsDao().addGroup(
                GroupEntity(
                    res.id,
                    res.name,
                    res.handle,
                    res.ownerId
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

    suspend fun leaveGroup(handle: String, password: String): Result<GroupResponse> {
        return try {
            val res = groupsService.leaveGroup(handle, password, tokenRepository.getScopeId())
                .body<GroupResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getAllGroupsOfUser(): Result<List<GroupResponse>> {
        return try {
            val res = groupsService.getAllGroupsOfUser(tokenRepository.fetchAuthToken())
                .body<List<GroupResponse>>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getAllMembers(handle: String): Result<List<MemberResponse>> {
        return try {
            val res = groupsService.getAllMembers(handle, tokenRepository.fetchAuthToken())
                .body<List<MemberResponse>>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun changeGroup(
        name: String,
        handle: String,
        password: String,
        confirmPassword: String
    ): Result<GroupResponse> {
        return try {
            val res = groupsService.changeGroup(
                name,
                handle,
                password,
                confirmPassword,
                tokenRepository.fetchAuthToken()
            )
                .body<GroupResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun removeMember(
        groupHandle: String,
        userHandle: String
    ): Result<GroupResponse> {
        return try {
            val res = groupsService.removeMember(groupHandle, userHandle, tokenRepository.fetchAuthToken()).body<GroupResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}