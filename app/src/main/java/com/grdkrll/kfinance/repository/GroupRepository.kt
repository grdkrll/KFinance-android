package com.grdkrll.kfinance.repository

import com.grdkrll.kfinance.model.database.GroupsDatabase
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.model.dto.groups.response.MemberResponse
import com.grdkrll.kfinance.model.table.GroupEntity
import com.grdkrll.kfinance.service.GroupsService
import io.ktor.client.call.*
import org.koin.core.component.getScopeId

/**
 * A Repository class used to make calls about Groups to the local database and the backend
 *
 * @param groupsService an instance of [GroupsService]
 * @param tokenRepository an instance of [TokenRepository]
 * @param database an instance of [GroupsDatabase]
 * @param selectedGroupRepository an instance of [SelectedGroupRepository]
 */
class GroupRepository(
    private val groupsService: GroupsService,
    private val tokenRepository: TokenRepository,
    private val database: GroupsDatabase,
    private val selectedGroupRepository: SelectedGroupRepository
) {

    /**
     * Used to select a Group
     *
     * @param id the id of the Group to select
     * @param name the name of the Group to select
     */
    fun selectGroup(id: Int, name: String) = selectedGroupRepository.saveGroup(id, name)

    /**
     * Used to deselect a selected Group
     */
    fun deselectGroup() = selectedGroupRepository.deselectGroup()

    /**
     * Used to fetch data about the selected Group
     *
     * @return an instance of [com.grdkrll.kfinance.model.Group] that holds data of the selected Group
     */
    fun fetchGroup() = selectedGroupRepository.fetchGroup()

    /**
     * Used to make an API call to the backend to create a new Group
     *
     * @param name the name of the new Group
     * @param handle the handle of the new Group
     * @param password the password of the new Group
     *
     * @return an instance of [Result] class that if successful holds an instance of [GroupResponse]
     */
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

    /**
     * Used to make an API call to the backend to add a new member to / join in the Group
     *
     * @param handle the handle of the Group
     * @param password the password of the Group
     *
     * @return an instance of [Result] class that if successful holds an instance of [GroupResponse]
     */
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

    /**
     * Used to make an API call to the backend to leave the Group
     *
     * @param handle the handle of the Group
     * @param password the password of the Group
     *
     * @return an instance of [Result] class that if successful holds an instance of [GroupResponse]
     */
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

    /**
     * Used make an API call to the backend to get all the Groups of the User
     *
     * @return an instance of [Result] class that if successful holds a List of instances of [GroupResponse]
     */
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

    /**
     * Used to make an API call to the backend to get all Members of the Group
     *
     * @return an instance of [Result] class that if successful holds a List of instances of [MemberResponse]
     */
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

    /**
     * Used make an API call to the backend to change the Group data
     *
     * @param name a new name for the Group
     * @param handle a new handle for the Group
     * @param password a new password for the Group (leave empty to keep current password)
     * @param confirmPassword the current password for the Group
     *
     * @return an instance of [Result] class that if successful holds an instance of [GroupResponse]
     */
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


    /**
     * Used to make an API call to the backend to remove a Member from the Group
     *
     * @param groupHandle the handle of the Group
     * @param userHandle the handle of the Member
     *
     * @return an instance of [Result] class that if successful holds an instance of [GroupResponse]
     */
    suspend fun removeMember(
        groupHandle: String,
        userHandle: String
    ): Result<GroupResponse> {
        return try {
            val res = groupsService.removeMember(
                groupHandle,
                userHandle,
                tokenRepository.fetchAuthToken()
            ).body<GroupResponse>()
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}