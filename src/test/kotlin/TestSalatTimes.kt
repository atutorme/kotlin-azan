fun main() {
    val salatTimes = SalatTimes()

    println("sunrise: ${salatTimes.sunrise.toHMS()}")
    println("sunset: ${salatTimes.sunset.toHMS()}")
    println("dhuhr: ${salatTimes.dhuhr.toHMS()}")
    println("maghrib: ${salatTimes.maghrib.toHMS()}")
    println("fajr: ${salatTimes.fajr.toHMS()}")
    println("isha: ${salatTimes.isha.toHMS()}")
}