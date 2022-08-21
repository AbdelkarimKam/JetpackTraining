package com.example.jetpacktrainning.tools

import android.content.Context
import android.widget.Toast


fun displayLoading(context: Context) = showToast(context, "Loading...")

fun displayError(context: Context, message: String?) =
    showToast(context, "Failed to load the data ${message ?: "Unknown error!"}")

private fun showToast(context: Context, text: String) =
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
