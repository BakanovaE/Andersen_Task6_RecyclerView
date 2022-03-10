package org.martellina.task5_fragments

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person (
    var name: String,
    var surname: String,
    var number: String,
    var imgUrl: String,
    var id: Int
) : Parcelable