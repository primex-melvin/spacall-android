// app/src/main/java/ph/spacall/android/TherapistAdapter.kt

package ph.spacall.android

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class TherapistAdapter(
    private val therapists: List<Therapist>,
    private val onTherapistClick: (Therapist) -> Unit
) : RecyclerView.Adapter<TherapistAdapter.TherapistViewHolder>() {

    inner class TherapistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImage: ImageView = itemView.findViewById(R.id.therapistAvatar)
        val nameText: TextView = itemView.findViewById(R.id.therapistName)
        val ratingText: TextView = itemView.findViewById(R.id.therapistRating)
        val reviewsText: TextView = itemView.findViewById(R.id.therapistReviews)
        val distanceText: TextView = itemView.findViewById(R.id.therapistDistance)
        val specialtyText: TextView = itemView.findViewById(R.id.therapistSpecialty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TherapistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_therapist, parent, false)
        return TherapistViewHolder(view)
    }

    override fun onBindViewHolder(holder: TherapistViewHolder, position: Int) {
        val therapist = therapists[position]

        // 1. Bind Data to Views
        holder.nameText.text = therapist.name
        holder.ratingText.text = "★ ${therapist.rating}"
        holder.reviewsText.text = "(${therapist.reviews} reviews)"
        holder.distanceText.text = therapist.distance
        holder.specialtyText.text = therapist.specialty

        // 2. Load Avatar Image using Glide
        Glide.with(holder.itemView.context)
            .load(therapist.avatarUrl)
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    // If image fails to load, show default avatar
                    holder.avatarImage.setImageResource(R.drawable.default_profile)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(holder.avatarImage)

        // 3. Handle Click to Open Dynamic Profile Detail Page
        holder.itemView.setOnClickListener {
            // First, trigger the existing map centering logic if needed
            onTherapistClick(therapist)

            // Then, launch the Detail Activity
            val intent = Intent(holder.itemView.context, TherapistDetailActivity::class.java)

            // Pass the specific data to the new page
            intent.putExtra("EXTRA_NAME", therapist.name)
            intent.putExtra("EXTRA_SPECIALTY", therapist.specialty)
            intent.putExtra("EXTRA_DISTANCE", therapist.distance)

            // Start the new page
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = therapists.size
}