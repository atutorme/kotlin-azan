enum class CalculationMethod(val niceName: String,
                             val fajrAngle: Double,
                             val ishaAngle: Double,
                             val overrideIshaDelayMins: Double? = null) {
    MUSLIM_WORLD_LEAGUE("Muslim World League", 18.0, 17.0),
    ISLAMIC_SOCIETY_OF_NORTH_AMERICA("Islamic Society of North America (ISNA)", 15.0, 15.0),
    EGYPTIAN_GENERAL_AUTHORITY_OF_SURVEY("Egyptian General Authority of Survey", 19.5, 17.5),
    UMM_AL_QURA_UNIVERSITY_MAKKAH("Umm al-Qura University, Makkah", 18.5, 0.0, 90.0),
    UNIVERSITY_OF_ISLAMIC_SCIENCES_KARACHI("University of Islamic Sciences, Karachi", 18.0, 18.0),
    INSTITUTE_OF_GEOPHYSICS_UNIVERSITY_OF_TEHRAN("Institute of Geophysics, University of Tehran", 17.7, 14.0),
    SHIA_ITHNA_ASHARI_LEVA_RESEARCH_INSTITUTE_QUM("Shia Ithna Ashari, Leva Research Institute, Qum", 16.0, 14.0)
}

enum class JuristicMethod(niceName: String) {
    MAJORITY("Majority"),
    HANAFI("Hanafi")
}