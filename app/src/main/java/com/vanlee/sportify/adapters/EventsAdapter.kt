package com.vanlee.sportify.adapters

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vanlee.sportify.database.objectbox.entities.DbEventItem
import com.vanlee.sportify.databinding.RecyclerItemEventBinding
import com.vanlee.sportify.utils.DateUtils.Companion.convertedDateToLocalTime


class EventsAdapter(
    private var context: Context,
    private var events: List<DbEventItem>
) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RecyclerItemEventBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(events[position]) {
                Glide.with(context)
                    .load(this.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    /*.placeholder(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_sports_current_event
                        )
                    )
                    .error(ContextCompat.getDrawable(context, R.drawable.ic_sports_current_event))*/
                    .into(binding.image)

                binding.title.text = this.title
                binding.subTitle.text = this.subTitle

                val calendar = convertedDateToLocalTime(this.rawTime)
                if (calendar != null) {
                    if (DateUtils.isToday(calendar.time.time)) {
                        binding.time.text = this.formattedTime
                    } else {
                        val dateTime = this.formattedDate + ", " + this.formattedTime
                        binding.time.text = dateTime
                    }
                } else {
                    val dateTime = this.formattedDate + ", " + this.formattedTime
                    binding.time.text = dateTime
                }
            }
        }
    }

    override fun getItemCount() = events.size

    companion object {
        private val TAG = EventsAdapter::class.java.simpleName
    }
}