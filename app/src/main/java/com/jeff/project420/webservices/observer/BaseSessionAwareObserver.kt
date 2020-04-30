package com.jeff.project420.webservices.observer

interface BaseSessionAwareObserver {

    fun onSessionExpiredError()

    fun onCommonError(e: Throwable)
}
