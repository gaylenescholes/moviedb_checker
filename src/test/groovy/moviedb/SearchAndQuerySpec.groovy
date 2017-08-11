package moviedb

import spock.lang.Specification
import groovy.json.*
import spock.lang.Stepwise
import spock.lang.Unroll

class SearchAndQuerySpec extends Specification {

    def "User can search using the movie endpoint"() {
        given: "I have a valid API Key"
        def apiKey = "${System.properties['apiKey']}"
        def theBaseUrl = "https://api.themoviedb.org/3/search/movie"
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
        def apiKey = "${System.properties['apiKey']}"
        def theBaseUrl = "https://api.themoviedb.org/3/movie"
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
        11      | "The Star Wars Collection" // This expected to fail.
    }

    def "User can extend the search "() {
        given: "A valid search set"
        when: "I extend the set"
        then: "I get a refined response"
    }
}
