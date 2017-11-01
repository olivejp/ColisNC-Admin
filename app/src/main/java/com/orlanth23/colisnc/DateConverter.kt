package com.orlanth23.colisnc

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by orlanth23 on 29/10/2017.
 */

object DateConverter {
    private val TAG = DateConverter::class.java.name
    private val simpleDtoDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE)
    private val simpleUiDateFormat = SimpleDateFormat("dd MMM yyyy à HH:mm", Locale.FRANCE)
    private val simpleEntityDateFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.FRANCE)

    /**
     * Va renvoyer la date du jour au format yyyyMMddHHmmss en Long
     *
     * @return Long
     */
    val nowEntity: Long
        get() {
            val cal = Calendar.getInstance()
            return java.lang.Long.parseLong(simpleEntityDateFormat.format(cal.time))
        }

    /**
     * Va renvoyer la date du jour au format dd/MM/yyyy HH:mm:ss en String
     *
     * @return String
     */
    val nowDto: String
        get() {
            val cal = Calendar.getInstance()
            return simpleDtoDateFormat.format(cal.time)
        }

    /**
     * Transformation d'une date de type yyyyMMddHHmmss vers le format dd MMM yy HH:mm
     * @param dateEntity
     * @return
     */
    fun convertDateEntityToUi(dateEntity: Long?): String? {
        try {
            return simpleUiDateFormat.format(simpleEntityDateFormat.parse(dateEntity.toString()))
        } catch (e: ParseException) {
            Log.e(TAG, e.message, e)
        }

        return null
    }

    /**
     * Transformation d'une date de type dd/MM/yyyy HH:mm:ss vers le format yyyyMMddHHmmss en Long
     *
     * @param dateDto
     * @return Long
     */
    fun convertDateDtoToEntity(dateDto: String): Long {
        try {
            val dateConverted = simpleEntityDateFormat.format(simpleDtoDateFormat.parse(dateDto))
            return java.lang.Long.parseLong(dateConverted)
        } catch (e: ParseException) {
            Log.e(TAG, e.message, e)
        } catch (e1: NullPointerException) {
            Log.e(TAG, e1.message, e1)
        }

        return 0L
    }

    /**
     * Transformation d'une date de type yyyyMMddHHmmss vers le format dd/MM/yyyy HH:mm:ss en Long
     *
     * @param dateEntity
     * @return String
     */
    fun convertDateEntityToDto(dateEntity: Long?): String? {
        try {
            val dateConverted = simpleEntityDateFormat.parse(dateEntity.toString())
            return simpleDtoDateFormat.format(dateConverted)
        } catch (e: ParseException) {
            Log.e(TAG, e.message, e)
        }

        return null
    }
}
