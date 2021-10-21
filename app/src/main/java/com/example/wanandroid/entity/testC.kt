package com.example.wanandroid.entity

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class testC(val string:String?) :Serializable,Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(string)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<testC> {
        override fun createFromParcel(parcel: Parcel): testC {
            return testC(parcel)
        }

        override fun newArray(size: Int): Array<testC?> {
            return arrayOfNulls(size)
        }
    }
}