import Sun.Companion.MINUTES_IN_DAY
import Sun.Companion.SECONDS_IN_DAY
import java.time.LocalDateTime
import java.util.*
import kotlin.math.PI

fun Double.radians() = this * PI / 180.0
fun Double.degrees() = this * 180.0 / PI

val gregorianCalendar = GregorianCalendar()
fun Int.isLeapYear() = gregorianCalendar.isLeapYear(this)

fun LocalDateTime.removeTime() = LocalDateTime.of(year, month, dayOfMonth, 0, 0)

fun Double.toTimeString() : String = "${h.zeroPad()}:${m.zeroPad()}:${s.zeroPad()}"
fun Double.toHMS() : String = "${if (h == 0) "" else "${h}h "}${if (h == 0 && m == 0) "" else "${m}m "}${s}s"

val Double.dOverflow : Int get() = this.toInt()
val Double.h : Int get() = ((this * 24) % 24.0).toInt()
val Double.m : Int get() = ((this * MINUTES_IN_DAY) % 60.0).toInt()
val Double.s : Int get() = ((this * SECONDS_IN_DAY) % 60.0).toInt()

fun Long.secondsToHMS() : String = (this.toDouble() / (SECONDS_IN_DAY)).toHMS()

fun Double.dateTime(baseDateTime: LocalDateTime) : LocalDateTime =
    baseDateTime.removeTime().plusDays(dOverflow.toLong())
        .plusHours(h.toLong())
        .plusMinutes(m.toLong())
        .plusSeconds(s.toLong())

fun Int.zeroPad() : String = this.toString().padStart(2, '0')

fun Triple<Int, Int, Int>.toFractionOfDay() : Double =
    this.first.toDouble() / 24.0 + this.second.toDouble() / 1440.0 + this.third.toDouble() / 86400.0