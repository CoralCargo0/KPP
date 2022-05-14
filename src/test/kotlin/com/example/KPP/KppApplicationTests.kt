package com.example.KPP

import com.example.KPP.common.Constants
import com.example.KPP.dto.ResultDto
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.util.AssertionErrors.assertEquals
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.server.ResponseStatusException


@SpringBootTest
class KppApplicationTests {


    @Autowired
    val wac: WebApplicationContext? = null

    @Autowired
    private val service: InelasticClashService? = null


    @Test
    fun getResult() {
        service?.addClash("1", "50", "1", "0")
        val expectedResult = ResultDto(35.35533905932738)
        assertEquals("Checked", expectedResult, service?.getLastClashResult());
    }


    @Test
    fun testError() {
        if (wac == null) {
            println("Test is lost!")
        } else {
            val mvc: MockMvc = webAppContextSetup(wac!!).build();
            mvc.perform(
                get(
                    "/calculate?${Constants.WEIGHT_OF_FIRST}=fff&" +
                            "${Constants.SPEED_OF_FIRST}=fff&" +
                            "${Constants.WEIGHT_OF_SECOND}=fff&" +
                            "${Constants.SPEED_OF_SECOND}=fff"
                )
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andExpect(status().isUnprocessableEntity)
                .andExpect { result: MvcResult ->
                    assertTrue(result.resolvedException is ResponseStatusException)
                }
        }
    }
}
