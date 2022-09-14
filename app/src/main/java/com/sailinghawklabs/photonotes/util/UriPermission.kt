package com.sailinghawklabs.photonotes.util

import android.content.Context
import android.content.Intent
import android.net.Uri


class UriPermissionHelper(private val appContext: Context) {
    fun get(uri: Uri) {
        appContext.contentResolver
            .takePersistableUriPermission(uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
}

