package com.vanlee.sportify.network

import android.content.res.Resources
import android.util.Log
import com.vanlee.sportify.R
import com.vanlee.sportify.adapters.EventsAdapter
import com.vanlee.sportify.database.objectbox.ObjectBox
import com.vanlee.sportify.database.objectbox.entities.DbEventItem
import com.vanlee.sportify.entities.HttpResponse
import com.vanlee.sportify.utils.DateUtils.Companion.formatTo
import com.vanlee.sportify.utils.DateUtils.Companion.toDate
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class NetworkRequests {

    companion object {
        private val TAG = NetworkRequests::class.java.simpleName

        /**
         * Get events from API
         */
        fun getEvents(client: OkHttpClient): HttpResponse {
            val request = Request.Builder()
                .url("https://us-central1-dazn-sandbox.cloudfunctions.net/getEvents")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e(TAG, "Unexpected code $response")

                    return HttpResponse(
                        false,
                        Resources.getSystem().getString(R.string.connection_error)
                    )
                } else {
                    val responseString = response.body?.string()
                    val jsonArray = responseString?.let { JSONArray(it) }

                    if (jsonArray != null) {
                        val eventsBox = ObjectBox.store.boxFor(DbEventItem::class.java)

                        for (index in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(index)

                            val idRow = jsonObject.getInt("id")
                            val title = jsonObject.getString("title")
                            val subTitle = jsonObject.getString("subtitle")
                            val imageUrl = jsonObject.getString("imageUrl")
                            val videoUrl = jsonObject.getString("videoUrl")
                            val rawTime = jsonObject.getString("date")

                            val formattedDate = rawTime.toDate().formatTo("dd.MM.yyyy")
                            val formattedTime = rawTime.toDate().formatTo("HH:mm")

                            val eventItem = DbEventItem(
                                idRow,
                                title,
                                subTitle,
                                imageUrl,
                                videoUrl,
                                rawTime,
                                formattedDate,
                                formattedTime
                            )

                            eventsBox.put(eventItem)
                        }

                        return HttpResponse(true, null)
                    } else {
                        return HttpResponse(
                            false,
                            Resources.getSystem().getString(R.string.connection_error)
                        )
                    }
                }
            }
        }

    }
}