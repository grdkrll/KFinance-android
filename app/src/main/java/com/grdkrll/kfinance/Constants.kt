package com.grdkrll.kfinance

/**
 * Used to check the Handle. Handle is correct if it's at least one character long and has only letters, numbers or underscores
 *
 * @param handle the Handle to be checked
 *
 * @return false if the Handle is correct
 */
fun checkHandle(handle: String) = !handle.matches(Regex("\\w+"))

/**
 * Used to check the Email
 *
 * @param email the Email to be checked
 *
 * @return false if the Email is correct
 */
fun checkEmail(email: String) = !email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))

/**
 * Used to check the Password. The Password is correct if it's length is at least 8 characters and it has at least one number, one uppercase and one lowercase letter
 *
 * @param password the Password to be checked
 *
 * @return null if the Password is correct. Otherwise returns string describing the error
 */
fun checkPassword(password: String): String? {
    if (password.length < 8) {
        return "Password has to be at least 8 characters"
    } else if (password.find { it.isDigit() } == null) {
        return "Use at least 1 number in your password"
    } else if (password.find { it.isLetter() && it.uppercaseChar() == it } == null) {
        return "Use at least 1 uppercase letter in your password"
    } else if (password.find { it.isLetter() && it.lowercaseChar() == it } == null) {
        return "Use at least 1 lowercase letter in your password"
    }
    return null
}

/**
 * Holds all possible Navigation Destinations for the NavigationDispatcher class
 */
object NavDest {
    const val HOME = "home"

    const val LOGIN = "login"

    const val REGISTER = "register"

    const val ADD_TRANSACTION = "add_transaction"

    const val GROUPS_LIST = "groups_list"

    const val PROFILE = "profile"

    const val JOIN_GROUP = "join_group"

    const val CREATE_GROUP = "create_group"

    const val ALL_TRANSACTIONS = "all_transactions"

    const val GROUP_SETTINGS = "group_settings"
}

/**
 * Lists all currently possible Transaction Categories. Only [SALARY], [GIFTS] and [DIVIDENDS] count as income. All the other options except [ALL] count as expenses
 */
enum class TransactionCategory {
    // expenses
    HOUSING,
    TRANSPORTATION,
    FOOD,
    UTILITIES,
    CLOTHING,
    MEDICAL,
    SUPPLIES,
    PERSONAL,
    DEBT,
    INVESTING,
    EDUCATION,
    SAVINGS,
    DONATIONS,
    ENTERTAINMENT,

    // income
    SALARY,
    GIFTS,
    DIVIDENDS,
    ALL
}

/**
 * Lists all currently possible Time Periods
 */
enum class TimePeriodType {
    TODAY,
    THIS_WEEK,
    THIS_MONTH,
    ALL
}

const val SERVICE_BASE_URL = "http://khaki-rice-dream.loca.lt"