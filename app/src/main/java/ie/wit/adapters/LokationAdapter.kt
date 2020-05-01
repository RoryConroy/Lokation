package ie.wit.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.squareup.picasso.Picasso
import ie.wit.R
import ie.wit.fragments.ReportAllFragment
import ie.wit.fragments.ReportFragment
import ie.wit.main.LokationApp
import ie.wit.models.LokationModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_lokation.view.*

interface LokationListener {
    fun onLokationClick(lokation: LokationModel)
}

class LokationAdapter(options: FirebaseRecyclerOptions<LokationModel>,
                        private val listener: LokationListener?)
                        : FirebaseRecyclerAdapter<LokationModel,
                            LokationAdapter.LokationViewHolder>(options) {

    class LokationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(lokation: LokationModel, listener: LokationListener) {
            with(lokation) {
                itemView.tag = lokation
                itemView.Titleview.text = lokation.Title.toString()
                itemView.Shortview.text = lokation.Short.toString()

                if(listener is ReportAllFragment)
                    ; // Do Nothing, Don't Allow 'Clickable' Rows
                else
                    itemView.setOnClickListener { listener.onLokationClick(lokation) }

                if(lokation.isfavourite) itemView.imagefavourite.setImageResource(android.R.drawable.star_big_on)

                if(!lokation.profilepic.isEmpty()) {
                    Picasso.get().load(lokation.profilepic.toUri())
                        //.resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(itemView.imageIcon)
                }
                else
                    itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_lokation_round)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LokationViewHolder {

        return LokationViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.card_lokation, parent, false))
    }

    override fun onBindViewHolder(holder: LokationViewHolder, position: Int, model: LokationModel) {
        holder.bind(model,listener!!)
    }

    override fun onDataChanged() {
        // Called each time there is a new data snapshot. You may want to use this method
        // to hide a loading spinner or check for the "no documents" state and update your UI.
        // ...
    }
}
