package com.example.KPP

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.AssertionErrors.assertEquals


@SpringBootTest
class KppApplicationTests {

	@Autowired
	private val service: InelasticClashService? = null


	@Test
	fun getResult() {
		service?.addClash("1", "50","1","0")
		val expectedResult = ResultDto("35,355")
		assertEquals("Checked",expectedResult, service?.getLastClashResult());
	}

}
