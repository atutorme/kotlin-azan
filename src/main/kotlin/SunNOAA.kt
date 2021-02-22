import java.time.LocalDateTime
import kotlin.math.*

/**
 * This implementation of Sun calculations is taken from the document titled:
 * "General Solar Position Calculations" produced by NOAA Global Monitoring Division
 * https://www.esrl.noaa.gov/gmd/grad/solcalc/solareqns.PDF
 * Retrieved on 2021-02-19
 */
class SunNOAA(val dateTime: LocalDateTime = LocalDateTime.now(), val location: Location = Location()) {
    val numDaysInYear = if (dateTime.year.isLeapYear()) 366 else 365

    // Fractional year - radians
    val fractionalYear = 2.0 * PI / numDaysInYear * (dateTime.dayOfYear - 1.0 + (dateTime.hour - 12.0) / 24.0)

    // Equation of time - minutes
    val eqTime = 229.18 * (0.000075 + 0.001868 * cos(fractionalYear) - 0.032077 * sin(fractionalYear) -
            0.014615 * cos(2 * fractionalYear) - 0.040849 * sin(2 * fractionalYear))

    // Solar declination angle - radians
    val decl = 0.006918 - 0.399912 * cos(fractionalYear) + 0.070257 * sin(fractionalYear) -
            0.006758 * cos(2 * fractionalYear) + 0.000907 * sin(2 * fractionalYear) -
            0.002697 * cos(3 * fractionalYear) + 0.00148 * sin (3 * fractionalYear)

    // Time offset - minutes
    val timeOffset = eqTime + 4 * location.longitude - 60 * location.timeZone

    // True solar time - minutes
    val tst = dateTime.hour * 60 + dateTime.minute + dateTime.second / 60.0 + timeOffset

    // Hour angle - degrees
    val ha = (tst / 4.0) - 180.0

    // Solar zenith angle - radians
    val phi = asin(sin(location.latitude.radians()) * sin(decl) +
            cos(location.latitude.radians()) * cos(decl) * cos(ha.radians()))

    // Solar azimuth angle - degrees
//    val theta = 180.0 - acos(-(sin(location.latitude.radians()) * cos(phi) - sin(decl)) /
//            (cos(location.latitude.radians()) * sin(phi))).degrees()
    val theta = -(sin(location.latitude.radians()) * cos(phi) - sin(decl)) /
            (cos(location.latitude.radians()) * sin(phi))
}

