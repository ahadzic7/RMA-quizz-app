package ba.etf.rma21.projekat.data.api

class ApiConfig {
    companion object {
        var baseURL: String = "https://rma21-etf.herokuapp.com"


        fun postaviBaseURL(baseUrl: String) {
            baseURL = baseUrl
        }

        fun getbaseURL(): String =
            baseURL

    }
}