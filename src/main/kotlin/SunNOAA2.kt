import java.time.LocalDateTime

/**
 * Alternate implementation of Solar Calculations
 * Based on the spreadsheets from the NOAA website:
 * https://www.esrl.noaa.gov/gmd/grad/solcalc/calcdetails.html
 */
class SunNOAA2(val dateTime: LocalDateTime = LocalDateTime.now(), val location: Location = Location()) {
    val julianDay = dateTime.julianDay(location.timeZone)

    val julianCentury = julianDay.julianCentury()

    val geomMeanLongSun = julianCentury.geomMeanLongSun()

    val geomMeanAnomSun =julianCentury.geomMeanAnomSun()

    val eccentEarthOrbit =julianCentury.eccentEarthOrbit()

}