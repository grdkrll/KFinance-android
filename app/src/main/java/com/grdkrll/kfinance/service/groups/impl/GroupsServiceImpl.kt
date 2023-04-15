package com.grdkrll.kfinance.service.groups.impl

import com.grdkrll.kfinance.SERVICE_BASE_URL
import com.grdkrll.kfinance.model.dto.groups.request.AddMemberRequest
import com.grdkrll.kfinance.model.dto.groups.request.CreateGroupRequest
import com.grdkrll.kfinance.service.groups.GroupsService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*

class GroupsServiceImpl(
    private val client: HttpClient
) : GroupsService {

    companion object {
        private const val BASE_URL = SERVICE_BASE_URL
    }

    override suspend fun createGroup(
        name: String,
        handle: String,
        password: String,
        token: String?
    ) = client.post("$BASE_URL/g/create_group") {
        header(AuthScheme.Bearer, token)
        contentType(ContentType.Application.Json)
        setBody(CreateGroupRequest(name, handle, password))
    }

    override suspend fun addMember(
        handle: String,
        password: String,
        token: String?
    ) = client.post("$BASE_URL/g/add_member") {
        header(AuthScheme.Bearer, token)
        contentType(ContentType.Application.Json)
        setBody(AddMemberRequest(handle, password))
    }

    override suspend fun removeMember(
        handle: String,
        password: String,
        token: String?
    ) = client.post("$BASE_URL/g/remove_member") {
        header(AuthScheme.Bearer, token)
        contentType(ContentType.Application.Json)
        setBody(AddMemberRequest(handle, password))
    }

    override suspend fun getAllGroupsOfUser(
        token: String?
    ) = client.get("$BASE_URL/g/get_all") {
        header(AuthScheme.Bearer, token)
    }

}