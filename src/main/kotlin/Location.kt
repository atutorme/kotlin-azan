// GPS Coordinates of the Ka'bah - https://www.gps-coordinates.net/
val MAKKAH_LATTITUDE = 21.422411
val MAKKAH_LONGITUDE = 39.826218
val MAKKAH_ALTITUDE = 304.0
val MAKKAH_TIMEZONE = 3.0

data class Location (
    val name: String = "Makkah",
    val latitude: Double = MAKKAH_LATTITUDE,
    val longitude: Double = MAKKAH_LONGITUDE,
    val altitude: Double = MAKKAH_ALTITUDE,
    val timeZone: Double = MAKKAH_TIMEZONE,
)