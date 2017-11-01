package com.orlanth23.colisnc

/**
 * Created by orlanth23 on 29/10/2017.
 */
val URL_API_AGENCIES_REST = "https://opendata.arcgis.com/datasets/f39610c502074eda9b5e575870fdb4ee_0.geojson"
val URL_API_FIBRE_REST = "https://opendata.arcgis.com/datasets/41bbc1919fad49528d688395bfbac5be_0.geojson"

// Careful, JSON is heavy (51 Mb). Don't download this link with Data.
val URL_API_COUVERTURE_MOBILE_REST = "https://opendata.arcgis.com/datasets/37ff8020bb7c4585874c8eae4678e54b_0.geojson"
val URL_SUIVI_COLIS = "http://webtrack.opt.nc/ipswebtracking/"
val URL_SUIVI_SERVICE_OPT = "IPSWeb_item_events.asp"

val ID_PARCEL_TEST = "EZ036524985US"

val ENCODING_ISO = "iso8859-1"
val ENCODING_UTF_8 = "UTF-8"

val FIREBASE_DATABASE = "firebase_database"
val CLOUD_FIRESTORE = "cloud_firestore"

/**
 * Minutes we will wait before launch the sync
 */
val PERIODIC_SYNC_JOB_MINS: Long = 15

/**
 * How close to the end of the period the job should run
 */
val INTERVAL_SYNC_JOB_MINS: Long = 5

val KEY_ACTUALITE = "actualites"