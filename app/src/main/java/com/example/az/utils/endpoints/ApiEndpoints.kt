package com.example.az.utils.endpoints

object ApiEndpoints {


    // Fetch Airports
    const val AIRPORT = "v1/airport"

    // Fetch Vaccines
    const val VACCINE = "v1/vaccine"

    // Fetch Nationalities
    const val NATIONALITY = "v1/nationality"

    // Travel Plan
    const val TRAVEL_PLAN = "v1_private/travelplan"
    const val TRAVEL_PLAN_ID = "v1_private/travelplan/{id}"

    // Fetch Restrictions
    const val RESTRICTION = "v1/restriction/{from}/{to}"

    // auth  +
    const val LOGIN = "v1/login"
    const val SIGNUP = "v1/signup"
    const val SELF = "v1_private/self"


    const val TOKEN = "x-session-id"
}