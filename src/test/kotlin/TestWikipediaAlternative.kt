import java.time.LocalDate
import kotlin.math.*

fun main() {
    // https://en.wikipedia.org/wiki/Equation_of_time#Alternative_calculation

    val W = 360.0 / 365.24

    val D = 47 // days since 1 Jan

    val A = W * (D + 10)

//    val B = A + (360.0 / PI) * 0.0167 * sin(W * (D - 2))
    val B = A + 1.914 * sin((W * (D - 2)).radians())

    val C = (A - atan(tan(B.radians()) / cos(23.44.radians()))) / 180.0

    val EOT = 720 * (C - C.roundToInt())

    println(EOT)
}