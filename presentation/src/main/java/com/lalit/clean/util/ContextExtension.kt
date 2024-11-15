package com.lalit.clean.util

import android.content.Context
import android.widget.Toast

/**
 * Extension function on [Context] to show a toast message.
 *
 * It checks if the message is not null and then displays it as a toast with a short duration.
 *
 * @param message The message to be displayed in the toast. If null, the toast will not be shown.
 */
fun Context.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}