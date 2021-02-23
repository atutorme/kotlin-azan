import java.time.LocalDateTime
import java.time.temporal.JulianFields
import kotlin.math.*

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

    val sunAppLong : Double = sunTrueLong - 0.00569 - 0.00478 * sin(RADIANS(125.04-1934.136*julianCentury))

    val meanObliqEcliptic : Double = sunTrueLong-0.00569-0.00478*sin(RADIANS(125.04-1934.136*julianCentury))

    val obliqCorr : Double = meanObliqEcliptic+0.00256*cos(RADIANS(125.04-1934.136*julianCentury))

    val sunRtAscen : Double = DEGREES(atan2(cos(RADIANS(sunAppLong)),cos(RADIANS(obliqCorr))*sin(RADIANS(sunAppLong))))

    val sunDeclin : Double = DEGREES(asin(sin(RADIANS(obliqCorr))*sin(RADIANS(sunAppLong))))

    val varY : Double = tan(RADIANS(obliqCorr/2))*tan(RADIANS(obliqCorr/2))

    val eqOfTime : Double = 4*DEGREES(varY*sin(2*RADIANS(geomMeanLongSun))-2*eccentEarthOrbit*sin(RADIANS(geomMeanAnomSun))+4*eccentEarthOrbit*varY*sin(RADIANS(geomMeanAnomSun))*cos(2*RADIANS(geomMeanLongSun))-0.5*varY*varY*sin(4*RADIANS(geomMeanLongSun))-1.25*eccentEarthOrbit*eccentEarthOrbit*sin(2*RADIANS(geomMeanAnomSun)))

    val haSunrise : Double = DEGREES(acos(cos(RADIANS(90.833))/(cos(RADIANS(location.latitude))*cos(RADIANS(sunDeclin)))-tan(RADIANS(location.latitude))*tan(RADIANS(sunDeclin))))

    val solarNoon : Double = (720-4*location.longitude-eqOfTime+location.timeZone*60)/1440

    val sunriseTime : Double = (solarNoon*1440-haSunrise*4)/1440

    val sunsetTime : Double = (solarNoon*1440+haSunrise*4)/1440

    val sunlightDuration : Double = 8*haSunrise

    val trueSolarTime : Double = MOD(dateTime.hour * 60 + dateTime.minute +eqOfTime+4*location.longitude-60*location.timeZone,1440)

    val hourAngle : Double = IF(trueSolarTime/4<0,trueSolarTime/4+180,trueSolarTime/4-180)

    val solarZenithAngle : Double = DEGREES(acos(sin(RADIANS(location.latitude))*sin(RADIANS(sunDeclin))+cos(RADIANS(location.latitude))*cos(RADIANS(sunDeclin))*cos(RADIANS(hourAngle))))

    val solarElevationAngle : Double = 90-solarZenithAngle

    val approxAtmosphericRefraction : Double = IF(solarElevationAngle>85,0,IF(solarElevationAngle>5,58.1/tan(RADIANS(solarElevationAngle))-0.07/POWER(tan(RADIANS(solarElevationAngle)),3)+0.000086/POWER(tan(RADIANS(solarElevationAngle)),5),IF(solarElevationAngle>-0.575,1735+solarElevationAngle*(-518.2+solarElevationAngle*(103.4+solarElevationAngle*(-12.79+solarElevationAngle*0.711))),-20.772/tan(RADIANS(solarElevationAngle)))))/3600

    val solarElevationCorrectedForAtmRefraction = solarElevationAngle+approxAtmosphericRefraction

    val solarAzimuthAngle = IF(hourAngle>0,MOD(DEGREES(acos(((sin(RADIANS(location.latitude))*cos(RADIANS(solarZenithAngle)))-sin(RADIANS(sunDeclin)))/(cos(RADIANS(location.latitude))*sin(RADIANS(solarZenithAngle)))))+180,360),MOD(540-DEGREES(acos(((sin(RADIANS(location.latitude))*cos(RADIANS(solarZenithAngle)))-sin(RADIANS(sunDeclin)))/(cos(RADIANS(location.latitude))*sin(RADIANS(solarZenithAngle))))),360))
}