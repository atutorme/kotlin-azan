import kotlin.math.absoluteValue

enum class LatitudeCategory(val latitude: Double) {
    CATEGORY_1(48.0),
    CATEGORY_2(66.0),
    CATEGORY_3(90.0)
}

data class Location (
    val name: String = "Makkah",
    val latitude: Double = MAKKAH_LATITUDE,
    val longitude: Double = MAKKAH_LONGITUDE,
    val altitude: Double = MAKKAH_ALTITUDE,
    val timeZone: Double = MAKKAH_TIMEZONE,
) {
    companion object {
        // GPS Coordinates of the Ka'bah - https://www.gps-coordinates.net/
        const val MAKKAH_LATITUDE = 21.422411
        const val MAKKAH_LONGITUDE = 39.826218
        const val MAKKAH_ALTITUDE = 304.0
        const val MAKKAH_TIMEZONE = 3.0
    }

    val latitudeCategory: LatitudeCategory = when {
        latitude.absoluteValue <= LatitudeCategory.CATEGORY_1.latitude -> LatitudeCategory.CATEGORY_1
        latitude.absoluteValue <= LatitudeCategory.CATEGORY_2.latitude -> LatitudeCategory.CATEGORY_2
        else -> LatitudeCategory.CATEGORY_3
    }

    fun closestLocationForExtremeLatitudes() : Location = when(latitudeCategory) {
        LatitudeCategory.CATEGORY_1 -> Location(name, latitude, longitude, altitude, timeZone)
        else -> Location(name, LatitudeCategory.CATEGORY_1.latitude, longitude, altitude, timeZone)
    }
}