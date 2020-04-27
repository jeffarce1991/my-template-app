package com.jeff.project420.internet

import com.jeff.project420.exception.NoInternetException
import com.jeff.project420.utilities.anotation.EmitsError
import io.reactivex.Completable

interface RxInternet {

    @EmitsError(NoInternetException::class)
    fun isConnected(): Completable
}
