package com.nims.fruitful.data.service

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}
