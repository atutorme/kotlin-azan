import java.time.LocalDateTime
import kotlin.math.*

class SalatTimes(val dateTime: LocalDateTime = LocalDateTime.now(),
                 val location: Location = Location(),
                 val calculationMethod: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE,
                 val juristicMethod: JuristicMethod = JuristicMethod.MAJORITY,
                 val adjustForAltitude: Boolean = true
) {
    val sunNOAA2 = Sun(dateTime, location)

    val sunrise = if (adjustForAltitude) sunNOAA2.sunriseTimeAltitudeCorrected else sunNOAA2.sunriseTime
    val sunset = if (adjustForAltitude) sunNOAA2.sunsetTimeAltitudeCorrected else sunNOAA2.sunsetTime

    val dhuhr = sunNOAA2.solarNoon
    val maghrib = sunset

    val fajr = dhuhr - sunNOAA2.t(calculationMethod.fajrAngle, adjustForAltitude) / 360.0
    val isha = if (calculationMethod.overrideIshaDelayMins == null) dhuhr + sunNOAA2.t(calculationMethod.ishaAngle, adjustForAltitude) / 360.0
    else maghrib + calculationMethod.overrideIshaDelayMins

    val asr = a(juristicMethod.shadowLength)

    fun a(shadowLength: Double) : Double = (sin(acot(shadowLength + tan((location.latitude - sunNOAA2.sunDeclin).radians()))) /
                (cos(location.latitude.radians()) * cos(sunNOAA2.sunDeclin.radians())) -
                tan(location.latitude.radians()) * tan(sunNOAA2.sunDeclin.radians())) / 15.0
}

fun acot(x: Double) : Double = atan(1.0 / x)