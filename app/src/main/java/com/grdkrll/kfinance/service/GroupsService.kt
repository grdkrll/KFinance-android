package com.grdkrll.kfinance.service

import io.ktor.client.statement.*

/**
 * A Service Class used to make API calls to the backend about Groups
 */
interface GroupsService {
    /**
     * Used to make an API call to the backend to create a new Group
     *
     * @param name the name for the new Group
     * @param handle the handle for the new Group
     * @param password the password for the new Group
     * @param token a JWT token to Authorize the call
     */
    suspend fun createGroup(
        name: String,
        handle: String,
        password: String,
        token: String?
    ): HttpResponse

    /**
     * Used to make an API call to the backend to add a new member to / join in a Group
     *
     * @param handle the handle of the Group
     * @param password the password of the Group
     * @param token a JWT token to Authorize the call
     */
    suspend fun addMember(handle: String, password: String, token: String?): HttpResponse

    /**
     * Used to make an API call to the backend to leave a Group
     *
     * @param handle the handle of the Group
     * @param password the password of the Group
     * @param token a JWT token to Authorize the call
     */
    suspend fun leaveGroup(handle: String, password: String, token: String?): HttpResponse

    /**
     * Used to make an API call to the backend to get a List of all Groups of the User
     *
     * @param token a JWT token to Authorize the call
     */
    suspend fun getAllGroupsOfUser(token: String?): HttpResponse

    /**
     * Used to make an API call to the backend to change Group's data
     *
     * @param name the name of the Group
     * @param handle the handle of the Group
     * @param password the password of the Group
     * @param token a JWT token to Authorize the call
     */
    suspend fun changeGroup(
        name: String,
        handle: String,
        password: String,
        confirmPassword: String,
        token: String?
    ): HttpResponse

    /**
     * Used to make an API call to get a List of all members of the Group
     *
     * @param handle the handle of the Group
     * @param token a JWT token to Authorize the call
     */
    suspend fun getAllMembers(handle: String, token: String?): HttpResponse

    /**
     * Used to make an API call to the backend to remove a User from the Group
     *
     * @param groupHandle the handle of the Group
     * @param userHandle the handle of the User
     * @param token a JWT token to Authorize the call
     */
    suspend fun removeMember(groupHandle: String, userHandle: String, token: String?): HttpResponse
}