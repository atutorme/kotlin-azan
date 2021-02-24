import java.time.LocalDateTime
import java.time.temporal.JulianFields
import kotlin.math.*

/**
 * Alternate implementation of Solar Calculations
 * Based on the spreadsheets from the NOAA website:
 * https://www.esrl.noaa.gov/gmd/grad/solcalc/calcdetails.html
 */
class SunNOAA2(val dateTime: LocalDateTime = LocalDateTime.now(), val location: Location = Location()) {

    val julianDay : Double = JulianFields.JULIAN_DAY.getFrom(dateTime) - location.timeZone / 24.0

    val julianCentury : Double = (julianDay - 2451545.0) / 36525.0

    val geomMeanLongSun : Double = (280.46646 + julianCentury * (36000.76983 + julianCentury * 0.0003032)) % 360.0

    val geomMeanAnomSun : Double = 357.52911 + julianCentury * (35999.05029 - 0.0001537 * julianCentury)

    val eccentEarthOrbit : Double = 0.016708634 - julianCentury * (0.000042037 + 0.0000001267 * julianCentury)

    val sunEqOfCtr : Double =
        sin(geomMeanAnomSun.radians()) * (1.914602 - julianCentury * (0.004817 + 0.000014 * julianCentury)) +
                sin((2 * geomMeanAnomSun).radians()) * (0.019993 - 0.000101 * julianCentury) +
                sin((3*geomMeanAnomSun).radians()) * 0.000289

    val sunTrueLong : Double = geomMeanLongSun + sunEqOfCtr

    val sunTrueAnom : Double = geomMeanAnomSun + sunEqOfCtr

    val sunRadVector : Double = 1.000001018 * (1 - eccentEarthOrbit * eccentEarthOrbit) /
            (1 + eccentEarthOrbit * cos(sunTrueAnom.radians()))

    val sunAppLong : Double = sunTrueLong - 0.00569 - 0.00478 * sin((125.04-1934.136 * julianCentury).radians())

    val meanObliqEcliptic : Double = 23.0 + (26.0 + ((21.448 - julianCentury * (46.815 + julianCentury *
            (0.00059 - julianCentury * 0.001813)))) / 60.0 ) / 60.0

    val obliqCorr : Double = meanObliqEcliptic + 0.00256 * cos((125.04 - 1934.136 * julianCentury).radians())

    val sunRtAscen : Double = (atan2(cos(sunAppLong.radians()),cos(obliqCorr.radians()) * sin(sunAppLong.radians()))).degrees()

    val sunDeclin : Double = (asin(sin(obliqCorr.radians()) * sin(sunAppLong.radians()))).degrees()

    val varY : Double = tan((obliqCorr / 2.0).radians()) * tan((obliqCorr / 2.0).radians())

    val eqOfTime : Double = 4 * (varY * sin(2 * geomMeanLongSun.radians()) - 2 * eccentEarthOrbit * sin(geomMeanAnomSun.radians()) +
            4 * eccentEarthOrbit * varY * sin(geomMeanAnomSun.radians()) * cos(2 * geomMeanLongSun.radians()) -
            0.5 * varY * varY * sin(4 * geomMeanLongSun.radians()) -
            1.25 * eccentEarthOrbit * eccentEarthOrbit * sin(2 * geomMeanAnomSun.radians())).degrees()

    // See http://praytimes.org/wiki/Prayer_Times_Calculation for correction for higher elevation
    fun t(alpha: Double, applyAltitudeCorrection: Boolean = true) =
        (acos(cos((90 + alpha + if(applyAltitudeCorrection) + 0.0347 * sqrt(location.altitude) else 0.0).radians()) /
            (cos(location.latitude.radians()) * cos(sunDeclin.radians())) -
            tan(location.latitude.radians()) * tan(sunDeclin.radians()))).degrees()

    val haSunrise : Double = t(0.833, false)

    val haSunriseAltitudeCorrected : Double = t(0.833)

    val solarNoon : Double = (720.0 - 4.0 * location.longitude - eqOfTime + location.timeZone * 60.0) / 1440.0

    val sunriseTime : Double = (solarNoon * 1440.0 - haSunrise * 4.0) / 1440.0

    val sunriseTimeAltitudeCorrected : Double = (solarNoon * 1440.0 - haSunriseAltitudeCorrected * 4.0) / 1440.0

    val sunsetTime : Double = (solarNoon * 1440.0 + haSunrise * 4.0) / 1440.0

    val sunsetTimeAltitudeCorrected : Double = (solarNoon * 1440.0 + haSunriseAltitudeCorrected * 4.0) / 1440.0

    val sunlightDuration : Double = 8.0 * haSunrise

    val sunlightDurationAltitudeCorrected : Double = 8.0 * haSunriseAltitudeCorrected

    val trueSolarTime : Double = (dateTime.hour * 60.0 + dateTime.minute + eqOfTime + 4.0 * location.longitude -
            60.0 * location.timeZone) % 1440.0

    val hourAngle : Double = if ((trueSolarTime / 4.0) < 0)
        trueSolarTime / 4.0 + 180.0 else trueSolarTime / 4.0 - 180.0

    val solarZenithAngle : Double = (acos(sin(location.latitude.radians()) * sin(sunDeclin.radians()) +
            cos(location.latitude.radians()) * cos(sunDeclin.radians()) * cos(hourAngle.radians()))).degrees()

    val solarElevationAngle : Double = 90.0 - solarZenithAngle

    val approxAtmosphericRefraction : Double = when {
        solarElevationAngle > 85.0 -> 0.0
        solarElevationAngle > 5.0 -> 58.1 / tan(solarElevationAngle.radians()) -0.07 / (tan(solarElevationAngle.radians()).pow(3)) + 0.000086 / tan(solarElevationAngle.radians()).pow(5)
        solarElevationAngle > -0.575 -> 1735.0 + solarElevationAngle * (-518.2 + solarElevationAngle * (103.4 + solarElevationAngle * (-12.79 + solarElevationAngle*0.711)))
        else -> -20.772/tan(solarElevationAngle.radians())
    } / 3600.0

    val solarElevationCorrectedForAtmRefraction = solarElevationAngle + approxAtmosphericRefraction

    val solarAzimuthAngle = if (hourAngle > 0.0) ((acos(((sin(location.latitude.radians())*cos(solarZenithAngle.radians())) -
            sin(sunDeclin.radians())) / (cos(location.latitude.radians()) * sin(solarZenithAngle.radians())))).degrees() + 180.0) % 360.0
    else (540.0 - (acos(((sin(location.latitude.radians()) * cos(solarZenithAngle.radians())) - sin(sunDeclin.radians())) /
            (cos(location.latitude.radians()) * sin(solarZenithAngle.radians())))).degrees()) % 360.0
}