package com.example.KPP

import com.example.KPP.models.ClashJPARepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration

@SpringBootApplication
class Application : CommandLineRunner {
	@Autowired
	private val repository: ClashJPARepository? = null

	@Throws(Exception::class)
	override fun run(vararg args: String) {
		repository?.deleteAll()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(Application::class.java, *args)
		}
	}
}