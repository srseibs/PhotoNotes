package com.sailinghawklabs.photonotes.model

import android.provider.SyncStateContract
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sailinghawklabs.photonotes.Constants.TABLE_NAME
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity(
    tableName = TABLE_NAME,
    indices = [ Index(value = ["id"], unique = true) ]
)
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "dateUpdated") val dateUpdated: String = getDateNow(),
    @ColumnInfo(name = "imageUri") val imageUri: String? = null

)


fun getDateNow()
= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))