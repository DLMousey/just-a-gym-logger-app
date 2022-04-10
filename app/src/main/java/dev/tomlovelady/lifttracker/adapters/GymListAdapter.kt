package dev.tomlovelady.lifttracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import dev.tomlovelady.lifttracker.LiftTrackerDatabase
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.fragments.GymListFragmentDirections

class GymListAdapter : ListAdapter<Gym, GymListAdapter.GymViewHolder>(GymComparator()) {

    lateinit var database: LiftTrackerDatabase

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        return GymViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, database)
    }

    class GymViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gymNameView: TextView = itemView.findViewById(R.id.recyclerview_gymList_name)
        private val gymTypeView: TextView = itemView.findViewById(R.id.recyclerview_gymList_type)
        private val gymAddressView: TextView = itemView.findViewById(R.id.recyclerview_gymList_address)
        private val gymStartSessionButton: Button = itemView.findViewById(R.id.recyclerview_gymList_startSession)
        private val gymViewSessionButton: Button = itemView.findViewById(R.id.recyclerview_gymList_viewSessions)
        private val gymViewMenuButton: MaterialTextView = itemView.findViewById(R.id.recyclerview_gymList_menuButton)
        private val gymIdView: TextView = itemView.findViewById(R.id.recyclerview_gymList_id)

        fun bind(gym: Gym, database: LiftTrackerDatabase) {
            gymNameView.text = gym.name
            gymTypeView.text = gym.type
            gymAddressView.text = gym.address
            gymIdView.text = gym.gymId.toString()

            gymViewSessionButton.setOnClickListener {
                itemView.findNavController().navigate(GymListFragmentDirections.gymListToSessionList(
                    gym.gymId
                ))
            }

            val listener: PopupMenu.OnMenuItemClickListener = PopupMenu.OnMenuItemClickListener {
                if (it.itemId == R.id.menu_gym_list_context_delete) {
                    Toast.makeText(itemView.context, "Preparing to delete ${gym.name}", Toast.LENGTH_SHORT).show()
                    database.gymDao().deleteGym(gym)
                    return@OnMenuItemClickListener true
                }

                return@OnMenuItemClickListener false
            }

            gymViewMenuButton.setOnClickListener {
                val menu = PopupMenu(itemView.context, gymViewMenuButton)
                menu.inflate(R.menu.menu_gym_list_context)
                menu.show()
                menu.setOnMenuItemClickListener(listener)
            }
        }

        companion object {
            fun create(parent: ViewGroup): GymViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_gym_item, parent, false)

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