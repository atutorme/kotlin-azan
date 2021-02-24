import java.util.*
import kotlin.math.PI
import kotlin.math.sin

fun Double.radians() = this * PI / 180.0
fun Double.degrees() = this * 180.0 / PI

val gregorianCalendar = GregorianCalendar()
fun Int.isLeapYear() = gregorianCalendar.isLeapYear(this)

fun Double.toHMS() : String = "${h}:${m}:${s}"

val Double.h : Int get() = (this * 24).toInt()
val Double.m : Int get() = ((this * 60.0) % 60.0).toInt()
val Double.s : Int get() = ((this * 3600.0) % 60.0).toInt()