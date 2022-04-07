package dev.tomlovelady.lifttracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.tomlovelady.lifttracker.LiftTrackerApplication
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.fragments.GymListFragmentDirections
import dev.tomlovelady.lifttracker.fragments.SessionListFragmentDirections

class GymListAdapter : ListAdapter<Gym, GymListAdapter.GymViewHolder>(GymComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        return GymViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name, current.gymId)
    }

    class GymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gymItemView: TextView = itemView.findViewById(R.id.textView)
        private val gymIdView: TextView = itemView.findViewById(R.id.recyclerview_gymList_id)

        fun bind(text: String?, gymId: Long?) {
            gymItemView.text = text
            gymIdView.text = gymId.toString()

            itemView.setOnClickListener {
                itemView.findNavController().navigate(GymListFragmentDirections.gymListToSessionList(
                    gymId!!,
                ))
            }
        }

        companion object {
            fun create(parent: ViewGroup): GymViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)

                return GymViewHolder(view)
            }
        }
    }

    class GymComparator : DiffUtil.ItemCallback<Gym>() {
        override fun areItemsTheSame(oldItem: Gym, newItem: Gym): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Gym, newItem: Gym): Boolean {
            return oldItem.name == newItem.name
        }
    }
}