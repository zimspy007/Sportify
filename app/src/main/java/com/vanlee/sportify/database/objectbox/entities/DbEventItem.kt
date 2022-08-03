package com.vanlee.sportify.database.objectbox.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DbEventItem(
    @Id
    var id: Long = 0,
    var idRow: Int = 0,
    var title: String? = null,
    var subTitle: String? = null,
    var imageUrl: String? = null,
    var videoUrl: String? = null,
    var rawTime: String? = null,
    var formattedDate: String? = null,
    var formattedTime: String? = null
)