import java.time.LocalDateTime
import java.time.temporal.JulianFields
import java.util.*
import kotlin.math.PI

fun Double.radians() = this * PI / 180.0
fun Double.degrees() = this * 180.0 / PI

val gregorianCalendar = GregorianCalendar()
fun Int.isLeapYear() = gregorianCalendar.isLeapYear(this)

fun LocalDateTime.julianDay(timeZone: Double = 0.0) : Double = JulianFields.JULIAN_DAY.getFrom(this) +
        this.hour / 24.0 + this.minute / (1440.0) - timeZone / 24.0

fun Double.julianCentury() : Double = (this - 2451545.0) / 36525.0

fun Double.geomMeanLongSun() : Double = (280.46646 + this * (36000.76983 + this * 0.0003032)) % 360.0
