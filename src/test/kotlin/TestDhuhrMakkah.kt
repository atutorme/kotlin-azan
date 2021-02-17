import java.time.LocalDate
import java.util.*

fun main() {
    // Formula for dhuhr is given as: Dhuhr = 12 + TimeZone - Lng/15 - EqT.
    // Let's test this for Makkah
    val makkahTimeZone = TimeZone.getTimeZone("Asia/Riyadh").rawOffset  / (60 * 60 * 1000)
    println("Makkah Timezone: $makkahTimeZone")

    val makkahLongitude = 39.8579

    println("Equation of time: ${Sun(LocalDate.now()).equationOfTime}")

    val dhuhr = 12 + makkahTimeZone - makkahLongitude / 15 - Sun(LocalDate.now()).equationOfTime

    println("Dhuhr in makkah is: $dhuhr")
}