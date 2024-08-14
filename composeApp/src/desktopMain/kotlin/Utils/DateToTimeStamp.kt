import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun dateToTimestamp(dateString: String): Long? {
    return try {
        // Parse the date string to a LocalDate
        val localDate = LocalDate.parse(dateString)

        // Convert LocalDate to LocalDateTime at start of the day
        val localDateTime = localDate.atStartOfDay()

        // Convert LocalDateTime to Instant using system default time zone
        val instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant()

        // Get the timestamp (milliseconds since epoch)
        instant.toEpochMilli()
    } catch (e: DateTimeParseException) {
        // Handle the exception and return null or a default value
        null
    }
}

fun timestampToDate(timestamp: Long): String {
    // Convert the timestamp to an Instant
    val instant = Instant.ofEpochMilli(timestamp)

    // Convert the Instant to a LocalDateTime using system default time zone
    val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()

    // Format the LocalDateTime to a date string
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy")
    return localDateTime.toLocalDate().format(dateFormatter)
}