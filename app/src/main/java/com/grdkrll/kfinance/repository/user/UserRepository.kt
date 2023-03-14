package com.grdkrll.kfinance.repository.user

import com.grdkrll.kfinance.remote.service.user.UserService


class UserRepository(
    private val userService: UserService
) {
}