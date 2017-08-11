package moviedb

import groovy.json.JsonSlurper
import spock.lang.Specification

class JsonFormatSpec extends Specification {
    def detailFields = [
            "adult",
            "backdrop_path",
            "belongs_to_collection",
            "budget",
            "genres",
            "homepage",
            "id",
            "imdb_id",
            "original_language",
            "original_title",
            "overview",
            "popularity",
            "poster_path",
            "production_companies",
            "production_countries",
            "release_date",
            "revenue",
            "runtime",
            "spoken_languages",
            "status",
            "tagline",
            "video",
            "vote_average",
            "vote_count"
    ]

    def "Verify the list of fields in the movie detail record" () {
        given: "A valid URL"
        def apiKey = 'a462ae827cc9f0c11812c98d7a785f70'
        def theBaseUrl = "https://api.themoviedb.org/3/movie"
        def movie = "111"
        def movieUrl = "${theBaseUrl}/${movie}?api_key=$apiKey"
        when: "I call the API"
        def response = "$movieUrl".toURL().text
        def jsonData = new JsonSlurper().parseText(response)
        then: "I expect to get back specific fields."
        detailFields.collect {jsonData.hasProperty(it)}
    }

    def "Verify the list of fields in the search record" () {
        given: "A valid search URL"
        when: "I call that URL"
        and: "capture the json response"
        then: "the response should have all of the correct fields."
    }

    def "Verify the genre array" () {
        given: "a valid search query"
        when: "I make the API call"
        and: "I capture the json response"
        then: "the response contains an array of genres"
    }
}

