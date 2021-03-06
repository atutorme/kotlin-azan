import java.time.LocalDateTime

fun main() {
    val salatTimes = SalatTimes()

    println("fajr: ${salatTimes.fajr.toHMS()}")
    println("sunrise: ${salatTimes.sunrise.toHMS()}")
    println("dhuhr: ${salatTimes.dhuhr.toHMS()}")
    println("asr: ${salatTimes.asr.toHMS()}")
    println("sunset: ${salatTimes.sunset.toHMS()}")
    println("maghrib: ${salatTimes.maghrib.toHMS()}")
    println("isha: ${salatTimes.isha.toHMS()}")

    println(salatTimes.salatTimestamps)
    println(salatTimes.salatDateTimes)

    println(salatTimes.isha)
    println(salatTimes.tomorrowFajr())

    println("Yesterday's salat times")
    println(salatTimes.yesterdaysSalatTimes())

    println("Yesterday's salat times that overlap into today")
    println(salatTimes.yesterdaysSalatTimesThatOverlapInToday())

    println("Next salat")
    println(salatTimes.nextSalatTime())
    println("Current salat")
    println(salatTimes.currentSalatTime())

    println("Seconds to next salat")
    println("${salatTimes.secondsToNextSalat()} -- ${salatTimes.timeToNextSalat()}")
    println("Seconds since current salat")
    println("${salatTimes.secondsFromLastSalat()} -- ${salatTimes.timeFromLastSalat()}")



//    val salatTimesNuukGreenland = SalatTimes(location = Location("Nuuk", 64.175, -51.736, 16.0, -3.0))
//
//    println("NUUK fajr: ${salatTimesNuukGreenland.fajr.toHMS()}")
//    println("NUUK sunrise: ${salatTimesNuukGreenland.sunrise.toHMS()}")
//    println("NUUK dhuhr: ${salatTimesNuukGreenland.dhuhr.toHMS()}")
//    println("NUUK asr: ${salatTimesNuukGreenland.asr.toHMS()}")
//    println("NUUK sunset: ${salatTimesNuukGreenland.sunset.toHMS()}")
//    println("NUUK maghrib: ${salatTimesNuukGreenland.maghrib.toHMS()}")
//    println("NUUK isha: ${salatTimesNuukGreenland.isha.toHMS()}")
//
//    println(salatTimesNuukGreenland.salatTimestamps)
//
//    val salatTimesNuukGreenlandSummer = SalatTimes(location = Location("Nuuk", 64.175, -51.736, 16.0, -3.0),
//    dateTime = LocalDateTime.of(2021, 6, 30, 0, 0, 0), applyDaylightSavings = true)
//
//    println("NUUK Summer fajr: ${salatTimesNuukGreenlandSummer.fajr.toHMS()}")
//    println("NUUK Summer sunrise: ${salatTimesNuukGreenlandSummer.sunrise.toHMS()}")
//    println("NUUK Summer dhuhr: ${salatTimesNuukGreenlandSummer.dhuhr.toHMS()}")
//    println("NUUK Summer asr: ${salatTimesNuukGreenlandSummer.asr.toHMS()}")
//    println("NUUK Summer sunset: ${salatTimesNuukGreenlandSummer.sunset.toHMS()}")
//    println("NUUK Summer maghrib: ${salatTimesNuukGreenlandSummer.maghrib.toHMS()}")
//    println("NUUK Summer isha: ${salatTimesNuukGreenlandSummer.isha.toHMS()}")
//
//    println(salatTimesNuukGreenlandSummer.salatTimestamps)
}