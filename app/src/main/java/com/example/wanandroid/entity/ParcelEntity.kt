package com.example.wanandroid.entity

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class ParcelEntity(val id:Int,val name:String?,val t:testC?) :Serializable,Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(testC::class.java.classLoader)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeParcelable(t, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelEntity> {
        override fun createFromParcel(parcel: Parcel): ParcelEntity {
            return ParcelEntity(parcel)
        }

        override fun newArray(size: Int): Array<ParcelEntity?> {
            return arrayOfNulls(size)
        }
    }


}