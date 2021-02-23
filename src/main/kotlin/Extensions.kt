import java.time.LocalDateTime
import java.time.temporal.JulianFields
import java.util.*
import kotlin.math.PI

fun Double.radians() = this * PI / 180.0
fun Double.degrees() = this * 180.0 / PI

val gregorianCalendar = GregorianCalendar()
fun Int.isLeapYear() = gregorianCalendar.isLeapYear(this)

fun LocalDateTime.julianDay(timeZone: Double = 0.0) = JulianFields.JULIAN_DAY.getFrom(this) +
        this.hour / 24.0 + this.minute / (1440.0) - timeZone / 24.0