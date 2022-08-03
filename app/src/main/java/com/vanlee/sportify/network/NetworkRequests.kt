package com.vanlee.sportify.network

import android.content.res.Resources
import android.util.Log
import com.vanlee.sportify.R
import com.vanlee.sportify.database.objectbox.ObjectBox
import com.vanlee.sportify.database.objectbox.entities.DbEventItem
import com.vanlee.sportify.database.objectbox.entities.DbScheduleItem
import com.vanlee.sportify.entities.HttpResponse
import com.vanlee.sportify.utils.DateUtils.Companion.formatTo
import com.vanlee.sportify.utils.DateUtils.Companion.isTomorrow
import com.vanlee.sportify.utils.DateUtils.Companion.toDate
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray


class NetworkRequests {

    companion object {
        val TAG: String = NetworkRequests::class.java.simpleName
        const val EVENTS_HTTP_TAG = "EVENTS_HTTP_TAG"
        const val SCHEDULE_HTTP_TAG = "SCHEDULE_HTTP_TAG"

        /**
         * Get events from API
         */
        fun getEvents(client: OkHttpClient): HttpResponse {
            val request = Request.Builder()
                .url("https://us-central1-dazn-sandbox.cloudfunctions.net/getEvents")
                .tag(EVENTS_HTTP_TAG)
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e(TAG, "Unexpected code $response")

                    return HttpResponse(
                        false,
                        Resources.getSystem().getString(R.string.connection_error)
                    )
                } else {
                    Log.i(TAG, "Schedule updated")

                    val responseString = response.body?.string()
                    val jsonArray = responseString?.let { JSONArray(it) }

                    if (jsonArray != null) {
                        val eventsBox = ObjectBox.store.boxFor(DbEventItem::class.java)
                        eventsBox.removeAll()

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

                            if (!eventsBox.all.contains(eventItem)) {
                                eventsBox.put(eventItem)
                            }
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

        /**
         * Get schedules from API
         */
        fun getSchedules(client: OkHttpClient): HttpResponse {
            val request = Request.Builder()
                .url("https://us-central1-dazn-sandbox.cloudfunctions.net/getSchedule")
                .tag(SCHEDULE_HTTP_TAG)
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
                        val schedulesBox = ObjectBox.store.boxFor(DbScheduleItem::class.java)
                        schedulesBox.removeAll()

                        for (index in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(index)

                            val idRow = jsonObject.getInt("id")
                            val title = jsonObject.getString("title")
                            val subTitle = jsonObject.getString("subtitle")
                            val imageUrl = jsonObject.getString("imageUrl")
                            val rawTime = jsonObject.getString("date")

                            val formattedDate = rawTime.toDate().formatTo("dd.MM.yyyy")
                            val formattedTime = rawTime.toDate().formatTo("HH:mm")

                            val scheduleItem = DbScheduleItem(
                                idRow,
                                title,
                                subTitle,
                                imageUrl,
                                rawTime,
                                formattedDate,
                                formattedTime
                            )

                            if (!schedulesBox.all.contains(scheduleItem) && isTomorrow(rawTime)) {
                                schedulesBox.put(scheduleItem)
                            }
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

        /**
         * Cancel http request identified using a tag
         *
         * @param client http client
         * @param tag    request tag
         */
        fun cancelCallWithTag(client: OkHttpClient, tag: String?) {
            for (call in client.dispatcher.queuedCalls()) {
                if (call.request().tag() == tag) call.cancel()
            }
            for (call in client.dispatcher.runningCalls()) {
                if (call.request().tag() == tag) call.cancel()
            }
        }

    }
}