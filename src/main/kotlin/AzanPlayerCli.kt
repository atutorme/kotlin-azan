import java.util.*
import kotlin.concurrent.timerTask

val salatNamesToPlay = listOf(SalatNames.FAJR, SalatNames.DHUHR, SalatNames.ASR, SalatNames.MAGHRIB, SalatNames.ISHA)

fun main() {
    val location = Location(
        "Brisbane",
        -27.468968,
        153.023499,
        17.0,
        10.0
    )

    val salatTimes = SalatTimes(location = location)

    val timer = Timer("Azan Player Cli", false)

    scheduleNextAzan(salatTimes, timer)
}

fun scheduleNextAzan(salatTimes: SalatTimes, timer: Timer) {
    println("Scheduling salat: ${salatTimes.nextSalatTime()}")
    println("in: ${salatTimes.secondsToNextSalat()} seconds")

    timer.schedule(
        timerTask {
            println("It's azan time!")
            println("Salat: ${salatTimes.currentSalatTime()}")

            scheduleNextAzan(SalatTimes(location = salatTimes.location), timer)

        }, salatTimes.secondsToNextSalat() * 1000
    )
}