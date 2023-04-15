package com.grdkrll.kfinance.service.groups

import io.ktor.client.statement.*

interface GroupsService {
    suspend fun createGroup(name: String, handle: String, password: String, token: String?) : HttpResponse

    suspend fun addMember(handle: String, password: String, token: String?): HttpResponse

    suspend fun removeMember(handle: String, password: String, token: String?): HttpResponse

    suspend fun getAllGroupsOfUser(token: String?) : HttpResponse
}