package moviedb

import spock.lang.Specification

class HttpCallSpec extends Specification{
    //todo: Invalid user key
    //todo: User not logged in
    //todo: Happy Path : user identified and authenticated
    //todo: Threat : origination from a known attacker (throttling) - not sure how to do this

    //todo: Not all the methods will fail. Use Postman to determine which fail and the appropriate status codes
    def "Using the wrong Http method is blocked" () {
        given: "a well-formed request"
        when: "I send the request with the wrong http method"
        then: "I get a blocking response of some sort."
        where:
        method | responseCode
        "get" | 405
        "put" | 405
        "post" | 405
        "delete" | 405
        "info" | 405
        "head" | 405
        "options" | 405
    }
}
