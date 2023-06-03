package com.grdkrll.kfinance.model

/**
 * A Class used to get information about the User from the local database
 *
 * @property id the id of the User in the local database
 * @property name the name of the User in the local database
 * @property email the email of the User in the local database
 * @property handle the handle of the User in the local database
 */
class User(
    val id: Int,
    val name: String,
    val email: String,
    val handle: String
)

/**
 * A Class used to get information about the Selected Group
 *
 * @property id the id of the Selected Group in the local database
 * @property name the name of the Selected Group in the local database
 */
class Group(
    val id: Int,
    val name: String
)