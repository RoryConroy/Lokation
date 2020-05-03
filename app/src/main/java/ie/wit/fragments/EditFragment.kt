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
import ie.wit.utils.createLoader
import ie.wit.utils.hideLoader
import ie.wit.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: LokationApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editLokation: LokationModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as LokationApp

        arguments?.let {
            editLokation = it.getParcelable("editlokation")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.editShort.setText(editLokation!!.Short)
        root.editTitle.setText(editLokation!!.Title)
        root.editRating.setText(editLokation!!.Rating)
        root.editUpvotes.setText(editLokation!!.upvotes.toString())


        root.editUpdateButton.setOnClickListener {
            showLoader(loader, "Updating Lokation on Server...")
            updateLokationData()
            updateLokation(editLokation!!.uid, editLokation!!)
            updateUserLokation(app.currentUser.uid,
                               editLokation!!.uid, editLokation!!)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(lokation: LokationModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editlokation",lokation)
                }
            }
    }

    fun updateLokationData() {
        editLokation!!.Short = root.editShort.text.toString()
        editLokation!!.Title = root.editTitle.text.toString()
        editLokation!!.Rating= root.editRating.text.toString()
        editLokation!!.upvotes = root.editUpvotes.text.toString().toInt()
    }

    fun updateUserLokation(userId: String, uid: String?, lokation: LokationModel) {
        app.database.child("user-lokations").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(lokation)
                        activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, ReportFragment.newInstance())
                        .addToBackStack(null)
                        .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Lokation error : ${error.message}")
                    }
                })
    }

    fun updateLokation(uid: String?, lokation: LokationModel) {
        app.database.child("lokations").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(lokation)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Lokation error : ${error.message}")
                    }
                })
    }
}
