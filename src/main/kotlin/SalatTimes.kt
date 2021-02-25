import java.time.LocalDateTime
import kotlin.math.*

enum class SalatNames(val niceName: String) {
    FAJR("Fajr"),
    SUNRISE("Sunrise"),
    DHUHR("Dhuhr"),
    ASR("Asr"),
    MAGHRIB("Maghrib"),
    ISHA("Isha")
}

class SalatTimes(val dateTime: LocalDateTime = LocalDateTime.now(),
                 val location: Location = Location(),
                 val calculationMethod: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE,
                 val juristicMethod: JuristicMethod = JuristicMethod.MAJORITY,
                 val adjustForAltitude: Boolean = true,
                 val adjustForExtremeLatitudes: Boolean = true,
                 val applyDaylightSavings: Boolean = false
) {
    val sun = Sun(dateTime, location)
    val sunExtreme = Sun(dateTime, location.closestLocationForExtremeLatitudes())
    val dst = if (applyDaylightSavings) 1.0 / 24.0 else 0.0

    val sunrise = if (adjustForAltitude) sun.sunriseTimeAltitudeCorrected else sun.sunriseTime
    val sunset = if (adjustForAltitude) sun.sunsetTimeAltitudeCorrected else sun.sunsetTime

    val sunriseExtreme = if (adjustForAltitude) sunExtreme.sunriseTimeAltitudeCorrected else sunExtreme.sunriseTime
    val sunsetExtreme = if (adjustForAltitude) sunExtreme.sunsetTimeAltitudeCorrected else sunExtreme.sunsetTime

    val dhuhr = sun.solarNoon
    val maghrib = sunset

    val fajr = dhuhr - sun.t(calculationMethod.fajrAngle, adjustForAltitude) / 360.0
    val isha = if (calculationMethod.overrideIshaDelayMins == null) dhuhr + sun.t(calculationMethod.ishaAngle, adjustForAltitude) / 360.0
    else maghrib + calculationMethod.overrideIshaDelayMins

    val asr = dhuhr + a(juristicMethod.shadowLength, sun)

    val dhuhrExtreme = sunExtreme.solarNoon
    val maghribExtreme = sunsetExtreme

    val fajrExtreme = dhuhrExtreme - sunExtreme.t(calculationMethod.fajrAngle, adjustForAltitude) / 360.0
    val ishaExtreme = if (calculationMethod.overrideIshaDelayMins == null) dhuhrExtreme + sunExtreme.t(calculationMethod.ishaAngle, adjustForAltitude) / 360.0
    else maghribExtreme + calculationMethod.overrideIshaDelayMins

    val asrExtreme = dhuhrExtreme + a(juristicMethod.shadowLength, sunExtreme)

    val salatTimestamps: Map<String, String> get() = mapOf(
        SalatNames.FAJR.niceName to (dst + if (adjustForExtremeLatitudes) fajrExtreme else fajr).toHMS(),
        SalatNames.SUNRISE.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) sunriseExtreme else sunrise).toHMS(),
        SalatNames.DHUHR.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) dhuhrExtreme else dhuhr).toHMS(),
        SalatNames.ASR.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) asrExtreme else asr).toHMS(),
        SalatNames.MAGHRIB.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) maghribExtreme else maghrib).toHMS(),
        SalatNames.ISHA.niceName to (dst + if (adjustForExtremeLatitudes) ishaExtreme else isha).toHMS(),
    )

    fun a(shadowLength: Double, sun: Sun) : Double = acos(sin(acot(shadowLength + tan((location.latitude - sun.sunDeclin).radians()))) /
                (cos(location.latitude.radians()) * cos(sun.sunDeclin.radians())) -
                tan(location.latitude.radians()) * tan(sun.sunDeclin.radians())).degrees() / 360.0
}

fun acot(x: Double) : Double = atan(1.0 / x)