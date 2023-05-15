package com.grdkrll.kfinance.service

import io.ktor.client.statement.*

interface GroupsService {
    suspend fun createGroup(
        name: String,
        handle: String,
        password: String,
        token: String?
    ): HttpResponse

    suspend fun addMember(handle: String, password: String, token: String?): HttpResponse
    suspend fun leaveGroup(handle: String, password: String, token: String?): HttpResponse
    suspend fun getAllGroupsOfUser(token: String?): HttpResponse
    suspend fun changeGroup(
        name: String,
        handle: String,
        password: String,
        confirmPassword: String,
        token: String?
    ): HttpResponse

    suspend fun getAllMembers(handle: String, token: String?): HttpResponse
    suspend fun removeMember(groupHandle: String, userHandle: String, token: String?): HttpResponse
}