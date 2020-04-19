package com.jeff.project420.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
                 @ColumnInfo(name = "id")
                 @PrimaryKey(autoGenerate = true) var id: Int,
                 @ColumnInfo(name = "album_id")
                 var albumId: Int,
                 @ColumnInfo(name = "title")
                 var title: String,
                 @ColumnInfo(name = "url")
                 var url: String,
                 @ColumnInfo(name = "thumbnail_url")
                 var thumbnailUrl: String)
