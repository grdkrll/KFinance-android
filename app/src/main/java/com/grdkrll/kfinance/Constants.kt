package com.grdkrll.kfinance

object NavDest {
    const val HOME = "home"

    const val PRE_LOGIN = "pre_login"

    const val LOGIN = "login"

    const val REGISTER = "register"

    const val ADD_TRANSACTION = "add_transaction"
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

const val SERVICE_BASE_URL = "http://slimy-clowns-punch-194-186-53-186.loca.lt"