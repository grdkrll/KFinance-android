package com.grdkrll.kfinance.model.exception.user

/**
 * An Exception class called whenever a Sign Up error happens
 *
 * @property message the error message
 */
class SignUpException(
    override val message: String?
) : Exception()