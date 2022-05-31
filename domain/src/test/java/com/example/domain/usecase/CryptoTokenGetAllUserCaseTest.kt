package com.example.domain.usecase

import com.example.domain.core.error.ApiFailure
import com.example.domain.core.error.Failure
import com.example.domain.core.functional.Either
import com.example.domain.model.CoinInfo
import com.example.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Test
import kotlin.math.E

class CryptoTokenGetAllUserCaseTest {
    private val repository: Repository = mockk()
    private val userCase: CryptoTokenGetAllUserCase = CryptoTokenGetAllUserCase(repository)

    @Test
    fun `execute is Success`() = runBlocking {

        coEvery {
            repository.getCryptoTokenListing("")
        } returns Either.Right(
            listOf()
        )

        val actual = userCase("")
        val expected = Either.Right(listOf<CoinInfo>())

        assertEquals(expected, actual)


    }

    @Test
    fun `execute is fail`() = runBlocking {
        val error = ApiFailure.Connection
        val expected = Either.Left(error)
        coEvery {
            repository.getCryptoTokenListing("")
        } returns expected

        val actual = userCase("")

        assertEquals(expected, actual)


    }
}