package moviedb

import spock.lang.Specification

class ExceptionSpec extends Specification {

    def "When I search for an invalid movie I get an error message"() {
        given: "an invalid movie id"
        def apiKey = "${System.properties['apiKey']}"
        def theBaseUrl = "https://api.themoviedb.org/3/movie"
        def String movie = movieId.toString()
        def movieUrl = "${theBaseUrl}/${movie}?api_key=$apiKey"
        when: "I send the invalid request to the details endpoint"
        def response = "$movieUrl".toURL().text
        then: "A FileNotFoundException is thrown."
        java.io.FileNotFoundException ex = thrown()
        where:
        movieId | movieTitle
        12345   | "This should fail"
    }

    def "When I send a malformed request to the API I get a 401 response."() {
        given: "A request with invalid format."
        when: "I send that request to the API"
        then: "I get a response with status code 401."
    }

    def "When there is a system error, I get a 500 response."() {
        given: "I have a mock server I can control"
        // Utilize Spock's native mock engines to provide a backend service to the API
        when: "I make a call to the API when it is redirected to the mock server"
        then: "The api returns 500 status code"
        and: "A meaningful entry is made in the system log."
    }

    def "When the site is not online I get a 503 response."() {
        given: "The site is down."
        // Send to a specific ip address where the site is not hosted.
        when: "I make any call to the site"
        then: "I get a response with status code 503."
    }

    def "When the server is too slow, I get a 504 response."() {
        given: "I have a mock server I can control"
        // Utilize Spock's native mock engines to provide a backend service to the API
        when: "I make a call to the API"
        and: "The server does not return within parameters"
        then: "The API returns a 504 status"
        and: "A meaningful entry is made in the system log."
    }

}
