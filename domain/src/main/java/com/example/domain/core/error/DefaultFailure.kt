package com.example.domain.core.error

import com.example.domain.core.error.Failure

data class DefaultFailure(
    val exception: Exception,
) : Failure