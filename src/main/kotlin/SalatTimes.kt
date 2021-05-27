import Sun.Companion.MINUTES_IN_DAY
import java.time.LocalDateTime
import java.time.ZoneOffset
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

fun interface LocalDateTimeProvider {
    fun getLocalDateTime() : LocalDateTime
}

class SalatTimes(val localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() },
                 val location: Location = Location(),
                 val calculationMethod: CalculationMethod = CalculationMethod.MUSLIM_WORLD_LEAGUE,
                 val juristicMethod: JuristicMethod = JuristicMethod.MAJORITY,
                 val adjustForAltitude: Boolean = true,
                 val adjustForExtremeLatitudes: Boolean = true,
                 val applyDaylightSavings: Boolean = false
) {
    val dateTime: LocalDateTime = localDateTimeProvider.getLocalDateTime()
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

    val isha =
        if (calculationMethod.overrideIshaDelayMins == null) dhuhr + sun.t(calculationMethod.ishaAngle, adjustForAltitude) / 360.0
        else maghrib + calculationMethod.overrideIshaDelayMins / MINUTES_IN_DAY

    val asr = dhuhr + a(juristicMethod.shadowLength, sun)

    val dhuhrExtreme = sunExtreme.solarNoon

    val maghribExtreme = sunsetExtreme

    val fajrExtreme = dhuhrExtreme - sunExtreme.t(calculationMethod.fajrAngle, adjustForAltitude) / 360.0

    val ishaExtreme =
        if (calculationMethod.overrideIshaDelayMins == null) dhuhrExtreme + sunExtreme.t(calculationMethod.ishaAngle, adjustForAltitude) / 360.0
        else maghribExtreme + calculationMethod.overrideIshaDelayMins / MINUTES_IN_DAY

    val asrExtreme = dhuhrExtreme + a(juristicMethod.shadowLength, sunExtreme)

    val salatTimestamps: Map<String, String> get() = mapOf(
        SalatNames.FAJR.niceName to (dst + if (adjustForExtremeLatitudes) fajrExtreme else fajr).toTimeString(),
        SalatNames.SUNRISE.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) sunriseExtreme else sunrise).toTimeString(),
        SalatNames.DHUHR.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) dhuhrExtreme else dhuhr).toTimeString(),
        SalatNames.ASR.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) asrExtreme else asr).toTimeString(),
        SalatNames.MAGHRIB.niceName to (dst + if (adjustForExtremeLatitudes && location.latitudeCategory == LatitudeCategory.CATEGORY_3) maghribExtreme else maghrib).toTimeString(),
        SalatNames.ISHA.niceName to (dst + if (adjustForExtremeLatitudes) ishaExtreme else isha).toTimeString(),
        SalatNames.FIRST_THIRD.niceName to (dst + if (adjustForExtremeLatitudes) firstThird() else firstThirdExtreme()).toTimeString(),
        SalatNames.MIDNIGHT.niceName to (dst + if (adjustForExtremeLatitudes) midnight() else midnightExtreme()).toTimeString(),
        SalatNames.LAST_THIRD.niceName to (dst + if (adjustForExtremeLatitudes) lastThird() else lastThirdExtreme()).toTimeString(),
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

    fun a(shadowLength: Double, sun: Sun) : Double = acos(sin(acot(shadowLength + tan(abs(location.latitude - sun.sunDeclin).radians()))) /
                (cos(location.latitude.radians()) * cos(sun.sunDeclin.radians())) -
                tan(location.latitude.radians()) * tan(sun.sunDeclin.radians())).degrees() / 360.0

    // These are functions instead of properties to avoid a circular calculation of salat times -
    // i.e. the new property would call a new property for the day after and so on!
    fun tomorrowFajr() : Double = 1.0 + SalatTimes(
        { localDateTimeProvider.getLocalDateTime().plusDays(1) },
        location, calculationMethod, juristicMethod, adjustForAltitude, adjustForExtremeLatitudes, applyDaylightSavings).fajr

    fun tomorrowFajrExtreme() : Double = 1.0 + SalatTimes(
        { localDateTimeProvider.getLocalDateTime().plusDays(1) },
        location, calculationMethod, juristicMethod, adjustForAltitude, adjustForExtremeLatitudes, applyDaylightSavings).fajrExtreme

    fun nightDuration() : Double = tomorrowFajr() - maghrib

    fun nightDurationExtreme() : Double = tomorrowFajrExtreme() - maghribExtreme

    fun midnight() : Double = maghrib + nightDuration() / 2.0

    fun midnightExtreme() : Double = maghribExtreme + nightDurationExtreme() / 2.0

    fun firstThird() : Double = maghrib + nightDuration() / 3.0

    fun firstThirdExtreme() : Double = maghribExtreme + nightDurationExtreme() / 3.0

    fun lastThird() : Double = maghrib + nightDuration() * 2.0 / 3.0

    fun lastThirdExtreme() : Double = maghribExtreme + nightDurationExtreme() * 2.0 / 3.0

    // This is for getting times like isha, midnight, last third that are from yesterday but actually fall in today
    fun yesterdaysSalatTimes() : Map<String, LocalDateTime> = SalatTimes(
        { localDateTimeProvider.getLocalDateTime().minusDays(1) },
        location, calculationMethod, juristicMethod, adjustForAltitude, adjustForExtremeLatitudes, applyDaylightSavings)
        .salatDateTimes.map { "Yesterday's ${it.key}" to it.value }.toMap()

    fun yesterdaysSalatTimesThatOverlapInToday() : Map<String, LocalDateTime> = yesterdaysSalatTimes().filter {
        it.value.isAfter(dateTime.removeTime())
    }

    fun nextSalatTime(localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() }) : Pair<String, LocalDateTime> {
        val now = localDateTimeProvider.getLocalDateTime()
        val allTimes = salatDateTimes + yesterdaysSalatTimesThatOverlapInToday()
        return allTimes.toList()
            .sortedBy { it.second }
            .first { it.second.isAfter(now) }
    }

    fun currentSalatTime(localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() }) : Pair<String, LocalDateTime> {
        val now = localDateTimeProvider.getLocalDateTime()
        val allTimes = salatDateTimes + yesterdaysSalatTimesThatOverlapInToday()
        return allTimes.toList()
            .sortedBy { it.second }
            .last { it.second.isBefore(now) }
    }

    fun secondsToNextSalat(localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() }) : Long =
        nextSalatTime().second.toEpochSecond(ZoneOffset.UTC) - localDateTimeProvider.getLocalDateTime().toEpochSecond(ZoneOffset.UTC)

    fun secondsFromLastSalat(localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() }) : Long =
        localDateTimeProvider.getLocalDateTime().toEpochSecond(ZoneOffset.UTC) - currentSalatTime().second.toEpochSecond(ZoneOffset.UTC)

    fun timeToNextSalat(localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() }) : String = secondsToNextSalat(localDateTimeProvider).secondsToHMS()

    fun timeFromLastSalat(localDateTimeProvider: LocalDateTimeProvider = LocalDateTimeProvider { LocalDateTime.now() }) : String = secondsFromLastSalat(localDateTimeProvider).secondsToHMS()
}

fun acot(x: Double) : Double = atan(1.0 / x)