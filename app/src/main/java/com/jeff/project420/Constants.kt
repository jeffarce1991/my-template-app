package com.jeff.project420

class Constants private constructor() {
    object Gateways {
        const val COVID19API = "https://api.covid19api.com"
        const val JSONPLACEHOLDER = "https://jsonplaceholder.typicode.com"
    }

    object ApiExceptions {
        const val ERROR_CONTRACT_ID_IS_NULL = "contractId from Deal DTO is null"
        const val ERROR_EMPTY_RESULT = "Empty results[] from API request"
        const val ERROR_NULL_RESULT = "Null results[] from API request"
    }
}