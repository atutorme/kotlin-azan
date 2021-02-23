import java.time.LocalDateTime
import java.time.temporal.JulianFields
import kotlin.math.cos
import kotlin.math.sin

/**
 * Alternate implementation of Solar Calculations
 * Based on the spreadsheets from the NOAA website:
 * https://www.esrl.noaa.gov/gmd/grad/solcalc/calcdetails.html
 */
class SunNOAA2(val dateTime: LocalDateTime = LocalDateTime.now(), val location: Location = Location()) {

    val julianDay : Double = JulianFields.JULIAN_DAY.getFrom(dateTime) +
                dateTime.hour / 24.0 + dateTime.minute / (1440.0) - location.timeZone / 24.0

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


}