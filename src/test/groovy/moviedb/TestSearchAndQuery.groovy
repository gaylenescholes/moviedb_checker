package moviedb

import spock.lang.Specification
import groovy.json.*
import spock.lang.Stepwise
import spock.lang.Unroll

class TestSearchAndQuery extends Specification {
    def apiKey
    def theBaseUrl

    def setup() {
//        apiKey = System.getProperty("apiKey")
        apiKey = 'a462ae827cc9f0c11812c98d7a785f70'

        theBaseUrl = "https://api.themoviedb.org/3/search/movie"
    }

    def "User can search using the movie endpoint"() {
        given: "I have a valid API Key"
        apiKey = 'a462ae827cc9f0c11812c98d7a785f70'
//        apiKey = System.getProperty("apiKey")
//        theBaseUrl = "https://api.themoviedb.org/3/search/movie?"
        and: "I have a query"
        def lookingFor = URLEncoder.encode("Jack Reacher").toString()
        def queryString = [api_key: "$apiKey", query: "$lookingFor"].collect { k, v -> "$k=$v" }.join('&')
        //and: "I have a way to read Json data"
        when: "I get the data from the url"
        def jsonText = "$theBaseUrl?$queryString".toURL().text
        def json = new JsonSlurper().parseText(jsonText)
        def count = json.total_results
        def movies = json.results
        then: "I get data I want"
        movies[0].id == 75780
        movies[0].title == "Jack Reacher"
        movies[0].video == false
        movies[1].id == 343611
        movies[1].title == "Jack Reacher: Never Go Back"
        movies[1].video == false

    }

    @Unroll
    def "User can search for movie details"() {
        given: "a specific movie id"
        apiKey = 'a462ae827cc9f0c11812c98d7a785f70'
        theBaseUrl = "https://api.themoviedb.org/3/movie"
        def String movie = movieId.toString()
        def movieUrl = "${theBaseUrl}/${movie}?api_key=${apiKey}"
        when: "I send a get message to the details endpoint"
        def response = "$movieUrl".toURL().text
        def jsonData = new JsonSlurper().parseText(response)
        then: "get get back expected values"
        jsonData.id == movieId
        jsonData.original_title == movieTitle
        where:
        movieId | movieTitle
        75780   | "Jack Reacher"
        343611  | "Jack Reacher: Never Go Back"
    }

def "When I search for an invalid movie I get an error message" () {
    given: "a specific movie id"
    apiKey = 'a462ae827cc9f0c11812c98d7a785f70'
    theBaseUrl = "https://api.themoviedb.org/3/movie"
    def String movie = movieId.toString()
    def movieUrl = "${theBaseUrl}/${movie}?api_key=$apiKey"
    when: "I send a get message to the details endpoint"
    def response = "$movieUrl".toURL().text
    def jsonData = new JsonSlurper().parseText(response)
    then: "get back error values"
    jsonData.status_code == 34
    "The resource you requested could not be found.".equalsIgnoreCase(jasonData.status_message)
    where:
    movieId | movieTitle
    123456  | "This should fail"
}

    def "User can extend the search "() {
        given: "A valid search set"
        when: "I extend the set"
        then: "I get a refined response"
    }
}
