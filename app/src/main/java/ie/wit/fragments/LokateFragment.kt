package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.wit.R
import ie.wit.main.LokationApp
import ie.wit.models.LokationModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_lokate.view.*
import kotlinx.android.synthetic.main.login.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.util.HashMap
import kotlin.time.milliseconds


class LokateFragment : Fragment(), AnkoLogger {

    lateinit var app: LokationApp
    lateinit var loader : AlertDialog
    lateinit var eventListener : ValueEventListener
    var favourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as LokationApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_lokate, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_lokate)
        //allows the customer to rate their mood at the location through a rich interface experience




        setButtonListener(root)
        setFavouriteListener(root)
        return root;
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LokateFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun setButtonListener( layout: View) {
        layout.LokateButton.setOnClickListener {
            val title = layout.add_title.text.toString()
            val short= layout.add_short.text.toString()
            val rating= layout.add_rating.text.toString()
            when {
                layout.add_title.text.toString().isNullOrEmpty() -> {
                    context?.toast("You must enter a title in order to create a lokation!")
                }

                    layout.add_rating.text.toString().toInt() > 10 && layout.add_rating.text.toString().isNullOrEmpty() -> {
                        context?.toast("You have to add a valid rating to create a lokation!")
                }


                else -> {
                    writeNewLokation(
                        LokationModel(
                            profilepic = app.userImage.toString(),
                            isfavourite = favourite,
                            latitude = app.currentLocation.latitude,
                            longitude = app.currentLocation.longitude,
                            Title = title,
                            Short = short,
                            Rating= rating,
                            email = app.currentUser.email
                        )
                    )
                }
            }
        }


    }

    fun setFavouriteListener (layout: View) {
        layout.imagefavourite.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (!favourite) {
                    layout.imagefavourite.setImageResource(android.R.drawable.star_big_on)
                    favourite = true
                }
                else {
                    layout.imagefavourite.setImageResource(android.R.drawable.star_big_off)
                    favourite = false
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getTotalLokated(app.currentUser.uid)
    }

    override fun onPause() {
        super.onPause()
        if(app.currentUser.uid != null)
            app.database.child("user-lokations")
                    .child(app.currentUser.uid)
                    .removeEventListener(eventListener)
    }

    fun writeNewLokation(lokation: LokationModel) {
        // Create new lokation at /lokations & /lokations/$uid
        showLoader(loader, "Adding Lokation to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.currentUser.uid
        val key = app.database.child("lokations").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        lokation.uid = key
        val lokationValues = lokation.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/lokations/$key"] = lokationValues
        childUpdates["/user-lokations/$uid/$key"] = lokationValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }

    fun getTotalLokated(userId: String?) {
        eventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                info("Firebase Lokation error : ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    val lokation = it.getValue<LokationModel>(LokationModel::class.java)
                }

            }
        }

        app.database.child("user-lokations").child(userId!!)
            .addValueEventListener(eventListener)
    }
}
