package com.example.domain.core.error

import com.example.domain.core.error.Failure

interface FailureHandler {
    fun handleThrowable(throwable: Throwable): Failure
}