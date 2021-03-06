package dev.tomlovelady.lifttracker.adapters

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.tomlovelady.lifttracker.R
import dev.tomlovelady.lifttracker.entities.Session
import java.text.SimpleDateFormat
import java.util.*

class SessionListAdapter : ListAdapter<Session, SessionListAdapter.SessionViewHolder>(SessionComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        return SessionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(Date(current.startedAt))
    }

    class SessionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sessionItemView: TextView = itemView.findViewById(R.id.recyclerview_session_item_name)

        fun bind(date: Date) {
            sessionItemView.text = DateFormat.format("dd/MM/yyyy hh:mm:ss a", date)
        }

        companion object {
            fun create(parent: ViewGroup): SessionViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_session_item, parent, false)

                return SessionViewHolder(view)
            }
        }
    }

    class SessionComparator : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean {
            return oldItem.sessionId == newItem.sessionId
        }
    }
}