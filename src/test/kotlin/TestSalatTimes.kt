fun main() {
    val salatTimes = SalatTimes()

    println("fajr: ${salatTimes.fajr.toHMS()}")
    println("sunrise: ${salatTimes.sunrise.toHMS()}")
    println("dhuhr: ${salatTimes.dhuhr.toHMS()}")
    println("asr: ${salatTimes.asr.toHMS()}")
    println("sunset: ${salatTimes.sunset.toHMS()}")
    println("maghrib: ${salatTimes.maghrib.toHMS()}")
    println("isha: ${salatTimes.isha.toHMS()}")
}