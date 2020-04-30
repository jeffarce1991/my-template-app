package com.jeff.project420.utilities.exception

import com.jeff.project420.Constants.ApiExceptions.ERROR_NULL_RESULT


/**
 * Handy com.solutio.android.utilities.exception for when [ApiResponse.results] is null.
 * Duplicates [EmptyResultException].
 */
class NullResultException(message: String) : Throwable(message) {

    constructor() : this(ERROR_NULL_RESULT)
}
