package com.gatlingTest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class GetCatHTMLSimulation extends Simulation {

	val httpProtocol = http
		.baseUrl("https://cataas.com")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
		.userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36")

	val scn = scenario("GetCatHTMLSimulation")
		.exec(http("get_html")
			.get("/cat?html=true")
			.resources(http("get_cat")
			.get("/cat")))

	setUp(scn.inject(
		nothingFor(5),
		atOnceUsers(1),
		rampUsers(30) during 40,
		constantUsersPerSec(20) during 20
	)
	).protocols(httpProtocol)
}