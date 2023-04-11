package com.kl.demostep.common.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> parseJsonFromStringEither(str: String): T =
    Json { ignoreUnknownKeys = true }
        .decodeFromString<T>(str)

inline fun String.toJsonString(): String {
    return Json.encodeToString(this)
}