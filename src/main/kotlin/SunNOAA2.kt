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

    val sunAppLong : Double = M2-0.00569-0.00478*SIN(RADIANS(125.04-1934.136*G2))

    val meanObliqEcliptic : Double = M2-0.00569-0.00478*SIN(RADIANS(125.04-1934.136*G2))

    val obliqCorr : Double = Q2+0.00256*COS(RADIANS(125.04-1934.136*G2))

    val sunRtAscen : Double = DEGREES(ATAN2(COS(RADIANS(P2)),COS(RADIANS(R2))*SIN(RADIANS(P2))))

    val sunDeclin : Double = DEGREES(ASIN(SIN(RADIANS(R2))*SIN(RADIANS(P2))))

    val varY : Double = TAN(RADIANS(R2/2))*TAN(RADIANS(R2/2))

    val eqOfTime : Double = 4*DEGREES(U2*SIN(2*RADIANS(I2))-2*K2*SIN(RADIANS(J2))+4*K2*U2*SIN(RADIANS(J2))*COS(2*RADIANS(I2))-0.5*U2*U2*SIN(4*RADIANS(I2))-1.25*K2*K2*SIN(2*RADIANS(J2)))

    val haSunrise : Double = DEGREES(ACOS(COS(RADIANS(90.833))/(COS(RADIANS($B$2))*COS(RADIANS(T2)))-TAN(RADIANS($B$2))*TAN(RADIANS(T2))))

    val solarNoon : Double = (720-4*$B$3-V2+$B$4*60)/1440

    val sunriseTime : Double = (X2*1440-W2*4)/1440

    val sunsetTime : Double = (X2*1440+W2*4)/1440

    val sunlightDuration : Double = 8*W2

    val trueSolarTime : Double = MOD(E2*1440+V2+4*$B$3-60*$B$4,1440)

    val hourAngle : Double = IF(AB2/4<0,AB2/4+180,AB2/4-180)

    val solarZenithAngle : Double = DEGREES(ACOS(SIN(RADIANS($B$2))*SIN(RADIANS(T2))+COS(RADIANS($B$2))*COS(RADIANS(T2))*COS(RADIANS(AC2))))

    val solarElevationAngle : Double = 90-AD2

    val approxAtmosphericRefraction : Double = IF(AE2>85,0,IF(AE2>5,58.1/TAN(RADIANS(AE2))-0.07/POWER(TAN(RADIANS(AE2)),3)+0.000086/POWER(TAN(RADIANS(AE2)),5),IF(AE2>-0.575,1735+AE2*(-518.2+AE2*(103.4+AE2*(-12.79+AE2*0.711))),-20.772/TAN(RADIANS(AE2)))))/3600

    val solarElevationCorrectedForAtmRefraction = AE2+AF2

    val solarAzimuthAngle = IF(AC2>0,MOD(DEGREES(ACOS(((SIN(RADIANS($B$2))*COS(RADIANS(AD2)))-SIN(RADIANS(T2)))/(COS(RADIANS($B$2))*SIN(RADIANS(AD2)))))+180,360),MOD(540-DEGREES(ACOS(((SIN(RADIANS($B$2))*COS(RADIANS(AD2)))-SIN(RADIANS(T2)))/(COS(RADIANS($B$2))*SIN(RADIANS(AD2))))),360))
}