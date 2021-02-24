import java.util.*
import kotlin.math.PI
import kotlin.math.sin

fun Double.radians() = this * PI / 180.0
fun Double.degrees() = this * 180.0 / PI

val gregorianCalendar = GregorianCalendar()
fun Int.isLeapYear() = gregorianCalendar.isLeapYear(this)

fun Double.toHMS() : String = "${h.zeroPad()}:${m.zeroPad()}:${s.zeroPad()}"

val Double.h : Int get() = (this * 24).toInt()
val Double.m : Int get() = ((this * 24 * 60.0) % 60.0).toInt()
val Double.s : Int get() = ((this * 24 * 3600.0) % 60.0).toInt()

fun Int.zeroPad() : String = this.toString().padStart(2, '0')

fun Triple<Int, Int, Int>.toFractionOfDay() : Double =
    this.first.toDouble() / 24.0 + this.second.toDouble() / 1440.0 + this.third.toDouble() / 86400.0