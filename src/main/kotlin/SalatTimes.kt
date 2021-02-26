import java.time.LocalDateTime
import kotlin.math.*

enum class SalatNames(val niceName: String) {
    FAJR("Fajr"),
    SUNRISE("Sunrise"),
    DHUHR("Dhuhr"),
    ASR("Asr"),
    MAGHRIB("Maghrib"),
    ISHA("Isha"),
    FIRST_THIRD("First Third"),
    MIDNIGHT("Midnight"),
    LAST_THIRD("Last Third"),
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
        SalatNames.FIRST_THIRD.niceName to (dst + if (adjustForExtremeLatitudes) firstThird() else firstThirdExtreme()).toHMS(),
        SalatNames.MIDNIGHT.niceName to (dst + if (adjustForExtremeLatitudes) midnight() else midnightExtreme()).toHMS(),
        SalatNames.LAST_THIRD.niceName to (dst + if (adjustForExtremeLatitudes) lastThird() else lastThirdExtreme()).toHMS(),
    )

    val salatDateTimes: Map<String, LocalDateTime> get() = mapOf(
        SalatNames.FAJR.niceName to (dst + if (adjustForExtremeLatitudes) fajrExtreme else fajr).dateTime(dateTime),
        SalatNames.SUNRISE.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) sunriseExtreme else sunrise).dateTime(dateTime),
        SalatNames.DHUHR.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) dhuhrExtreme else dhuhr).dateTime(dateTime),
        SalatNames.ASR.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) asrExtreme else asr).dateTime(dateTime),
        SalatNames.MAGHRIB.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) maghribExtreme else maghrib).dateTime(dateTime),
        SalatNames.ISHA.niceName to (dst + if (adjustForExtremeLatitudes) ishaExtreme else isha).dateTime(dateTime),
        SalatNames.FIRST_THIRD.niceName to (dst + if (adjustForExtremeLatitudes) firstThird() else firstThirdExtreme()).dateTime(dateTime),
        SalatNames.MIDNIGHT.niceName to (dst + if (adjustForExtremeLatitudes) midnight() else midnightExtreme()).dateTime(dateTime),
        SalatNames.LAST_THIRD.niceName to (dst + if (adjustForExtremeLatitudes) lastThird() else lastThirdExtreme()).dateTime(dateTime),
    )

    fun a(shadowLength: Double, sun: Sun) : Double = acos(sin(acot(shadowLength + tan((location.latitude - sun.sunDeclin).radians()))) /
                (cos(location.latitude.radians()) * cos(sun.sunDeclin.radians())) -
                tan(location.latitude.radians()) * tan(sun.sunDeclin.radians())).degrees() / 360.0

    // I have made these as functions instead of properties to avoid a circular calculation of salat times - i.e. the new property would call a new property for the day after and so on!
    fun tomorrowFajr() : Double = 1.0 + SalatTimes(dateTime.plusDays(1),
        location, calculationMethod, juristicMethod, adjustForAltitude, adjustForExtremeLatitudes, applyDaylightSavings).fajr

    fun tomorrowFajrExtreme() : Double = 1.0 + SalatTimes(dateTime.plusDays(1),
        location, calculationMethod, juristicMethod, adjustForAltitude, adjustForExtremeLatitudes, applyDaylightSavings).fajrExtreme

    fun nightDuration() : Double = tomorrowFajr() - isha

    fun nightDurationExtreme() : Double = tomorrowFajrExtreme() - ishaExtreme

    fun midnight() : Double = isha + nightDuration() / 2.0

    fun midnightExtreme() : Double = ishaExtreme + nightDurationExtreme() / 2.0

    fun firstThird() : Double = isha + nightDuration() / 3.0

    fun firstThirdExtreme() : Double = ishaExtreme + nightDurationExtreme() / 3.0

    fun lastThird() : Double = isha + nightDuration() * 2.0 / 3.0

    fun lastThirdExtreme() : Double = ishaExtreme + nightDurationExtreme() * 2.0 / 3.0
}

fun acot(x: Double) : Double = atan(1.0 / x)