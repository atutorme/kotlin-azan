import Sun.Companion.epoch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    // https://equation-of-time.info/calculating-the-equation-of-time

    val now = LocalDate.now()

    val daysSinceJ2000 = ChronoUnit.DAYS.between(epoch, now)
    println("Days since epoch: $daysSinceJ2000")

    val n_deg = 23.439 - 0.0000004 * daysSinceJ2000
    val n_rad = n_deg.radians()

    val m_deg = (357.528 + 0.9856004 * daysSinceJ2000) % 360.0
    val m_rad = m_deg.radians()

    val g_deg = (280.460 + 0.9856474 * daysSinceJ2000) % 360.0
    val l_deg = (g_deg + 1.915 * sin(m_rad) + 0.020 * sin(2 * m_rad)) % 360.0
    val l_rad = l_deg.radians()

    val a_rad = atan((sin(l_rad) * cos(n_rad)) / cos(l_rad))
    val a_deg = a_rad.degrees() % 360.0

//    val a_hours = a_deg / 15
    val eot_deg = g_deg - a_deg
    val eot_min = 4 * eot_deg

    println("Equation of Time degrees: $eot_deg")
    println("Equation of Time minutes: $eot_min")
}