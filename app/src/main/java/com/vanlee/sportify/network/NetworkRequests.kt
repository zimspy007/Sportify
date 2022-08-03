package com.vanlee.sportify.network

import com.vanlee.sportify.entities.HttpResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject

class NetworkRequests {

    companion object {

        /**
         * Get events from API
         */
        fun getEvents(client: OkHttpClient): HttpResponse {
            val request = Request.Builder()
                .url("https://us-central1-dazn-sandbox.cloudfunctions.net/getEvents")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return HttpResponse(false, "Unexpected code $response")
                } else {
                    val responseString = response.body?.string()
                    val jsonArray = responseString?.let { JSONArray(it) }



                }
            }
            return HttpResponse(false, null)
        }

    }
}