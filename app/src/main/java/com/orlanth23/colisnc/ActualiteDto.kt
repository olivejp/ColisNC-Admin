package com.orlanth23.colisnc

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by orlanth23 on 29/10/2017.
 */

@IgnoreExtraProperties
class ActualiteDto : Parcelable {


    @Exclude
    var idActualite: String? = null

    @Exclude
    var idFirebase: String? = null

    var date: String? = null
    var titre: String? = null
    var type: String? = null
    var contenu: String? = null
    var isDismissable: Boolean = false
    var isDismissed: Boolean = false

    constructor() {}

    constructor(idActualite: String, idFirebase: String, date: String, titre: String, type: String, contenu: String, dismissable: Boolean, dismissed: Boolean) {
        this.idActualite = idActualite
        this.idFirebase = idFirebase
        this.date = date
        this.titre = titre
        this.type = type
        this.contenu = contenu
        this.isDismissable = dismissable
        this.isDismissed = dismissed
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.idActualite)
        dest.writeString(this.idFirebase)
        dest.writeString(this.date)
        dest.writeString(this.titre)
        dest.writeString(this.type)
        dest.writeString(this.contenu)
        dest.writeByte(if (this.isDismissable) 1.toByte() else 0.toByte())
        dest.writeByte(if (this.isDismissed) 1.toByte() else 0.toByte())
    }

    protected constructor(`in`: Parcel) {
        this.idActualite = `in`.readString()
        this.idFirebase = `in`.readString()
        this.date = `in`.readString()
        this.titre = `in`.readString()
        this.type = `in`.readString()
        this.contenu = `in`.readString()
        this.isDismissable = `in`.readByte().toInt() != 0
        this.isDismissed = `in`.readByte().toInt() != 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<ActualiteDto> = object : Parcelable.Creator<ActualiteDto> {
            override fun createFromParcel(source: Parcel): ActualiteDto {
                return ActualiteDto(source)
            }

            override fun newArray(size: Int): Array<ActualiteDto?> {
                return arrayOfNulls(size)
            }
        }
    }
}
