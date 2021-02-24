import java.time.LocalDateTime
import kotlin.math.*

class SalatTimes(val dateTime: LocalDateTime = LocalDateTime.now(),
                 val location: Location = Location(),
                 val calculationMethod: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE,
                 val juristicMethod: JuristicMethod = JuristicMethod.MAJORITY
) {
    val sunNOAA2 = SunNOAA2(dateTime, location)

//    val sunrise = sunNOAA2.sunriseTimeAltitudeCorrected
//    val sunset = sunNOAA2.sunsetTimeAltitudeCorrected

    val sunrise = sunNOAA2.sunriseTime
    val sunset = sunNOAA2.sunsetTime

    val dhuhr = sunNOAA2.solarNoon
    val maghrib = sunset

    val fajr = dhuhr - sunNOAA2.t(calculationMethod.fajrAngle) / 360.0
    val isha = if (calculationMethod.overrideIshaDelayMins == null) dhuhr + sunNOAA2.t(calculationMethod.ishaAngle) / 360.0
    else maghrib + calculationMethod.overrideIshaDelayMins

    val asr = a(juristicMethod.shadowLength)

    fun a(shadowLength: Double) : Double = (sin(acot(shadowLength + tan((location.latitude - sunNOAA2.sunDeclin).radians()))) /
                (cos(location.latitude.radians()) * cos(sunNOAA2.sunDeclin.radians())) -
                tan(location.latitude.radians()) * tan(sunNOAA2.sunDeclin.radians())) / 15.0
}

fun acot(x: Double) : Double = atan(1.0 / x)