import java.time.LocalDateTime

class SalatTimes(val dateTime: LocalDateTime = LocalDateTime.now(),
                 val location: Location = Location(),
                 val juristicMethod: JuristicMethod = JuristicMethod.MAJORITY
) {
    val sunNOAA2 = SunNOAA2(dateTime, location)

    val sunrise = sunNOAA2.sunriseTimeAltitudeCorrected
    val sunset = sunNOAA2.sunsetTimeAltitudeCorrected

    val dhuhr = sunNOAA2.solarNoon

//    val fajr = dhuhr -
}