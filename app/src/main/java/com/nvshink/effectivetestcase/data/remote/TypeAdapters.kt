package com.nvshink.effectivetestcase.data.remote

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object CustomDateAdapter {
    private val primaryFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val fallbackFormatters = listOf(
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd")
    )

    @FromJson
    fun fromJson(dateString: String?): LocalDate? {
        if (dateString.isNullOrBlank()) return null

        return try {
            // Try primary format first
            LocalDate.parse(dateString, primaryFormatter)
        } catch (e: DateTimeParseException) {
            // Try fallback formats
            fallbackFormatters.firstNotNullOfOrNull { formatter ->
                try {
                    LocalDate.parse(dateString, formatter)
                } catch (e: DateTimeParseException) {
                    null
                }
            } ?: throw DateTimeParseException(
                "Failed to parse date: $dateString. Supported formats: " +
                        listOf(primaryFormatter).plus(fallbackFormatters).joinToString { it.toString() },
                dateString,
                0
            )
        }
    }

    @ToJson
    fun toJson(date: LocalDate?): String? {
        return date?.let { primaryFormatter.format(it) }
    }
}