package ie.wit.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class LokationModel(
    var uid: String? = "",
    var Title: String ="",
    var Short: String="",
    var message: String = "a message",
    var upvotes: Int = 0,
    var Rating: String="",
    var profilepic: String = "",
    var isfavourite: Boolean = false,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var email: String? = "joe@bloggs.com")
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "message" to message,
            "Title" to Title,
            "Short" to Short,
            "Rating" to Rating,
            "upvotes" to upvotes,
            "profilepic" to profilepic,
            "isfavourite" to isfavourite,
            "latitude" to latitude,
            "longitude" to longitude,
            "email" to email
        )
    }
}


