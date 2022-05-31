package com.example.data.remote.retrofit

import com.example.data.remote.reponse.CoinInfoResponse
import com.example.data.remote.reponse.CoinListingResponse
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: CryptoApi

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = CryptoApi(mockWebServer.url("/").toString())
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Given coin type data, When get all, Should get those coin types`() = runBlocking {
        val items =
            CoinListingResponse(
                listOf(
                    CoinInfoResponse(
                        "LTC",
                        "USD",
                        BigDecimal("67.9009"),
                        BigDecimal("67.7312"),
                        "https://cdn.coinhako.com/assets/wallet-ltc-e4ce25a8fb34c45d40165b6f4eecfbca2729c40c20611acd45ea0dc3ab50f8a6.png",
                        "Litecoin"
                    )
                )
            )
        val json = "{\"data\": [\n" +
                "        {\n" +
                "            \"base\": \"LTC\",\n" +
                "            \"counter\": \"USD\",\n" +
                "            \"buy_price\": \"67.9009\",\n" +
                "            \"sell_price\": \"67.7312\",\n" +
                "            \"icon\": \"https://cdn.coinhako.com/assets/wallet-ltc-e4ce25a8fb34c45d40165b6f4eecfbca2729c40c20611acd45ea0dc3ab50f8a6.png\",\n" +
                "            \"name\": \"Litecoin\"\n" +
                "        }\n" +
                "        ]}"
        val response = MockResponse().setBody(json)
        mockWebServer.enqueue(response)
        Assert.assertEquals(items, service.getCoinListing("USD"))
    }
}