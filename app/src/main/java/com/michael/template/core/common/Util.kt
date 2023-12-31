package com.michael.template.core.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

fun displayToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun Dp.toPx(): Int = LocalDensity.current.run {
    roundToPx()
}

fun Any?.log(line: Any? = null) {
    Log.d("Multi-mate: line - ${line?.toString()}", this.toString())
}
