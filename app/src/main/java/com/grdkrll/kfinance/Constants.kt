package com.grdkrll.kfinance

object NavDest {
    const val HOME = "home"

    const val PRE_LOGIN = "pre_login"

    const val LOGIN = "login"

    const val REGISTER = "register"

    const val ADD_TRANSACTION = "add_transaction"

    const val GROUPS_LIST = "groups_list"

    const val PROFILE = "profile"

    const val ADD_GROUP = "add_group"

    const val CREATE_GROUP = "create_group"
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

const val SERVICE_BASE_URL = "http://dry-geckos-work-85-143-112-40.loca.lt"