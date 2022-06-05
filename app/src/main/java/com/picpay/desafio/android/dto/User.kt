package com.picpay.desafio.android.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id") var id: Int?,
    @SerializedName("img") var img: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("username") var username: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(img)
        parcel.writeString(name)
        parcel.writeString(username)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}