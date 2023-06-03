package com.grdkrll.kfinance.model.exception.user

/**
 * An Exception class called whenever a Sign In error happens
 *
 * @property message the error message
 */
class SignInException(
    override val message: String?
) : Exception()