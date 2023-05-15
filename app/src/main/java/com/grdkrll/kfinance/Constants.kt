package com.grdkrll.kfinance

fun checkHandle(handle: String) = !handle.matches(Regex("\\w+"))
fun checkEmail(email: String) = !email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))

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

const val SERVICE_BASE_URL = "http://slimy-items-write.loca.lt"