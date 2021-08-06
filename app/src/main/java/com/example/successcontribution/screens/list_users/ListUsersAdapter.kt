package com.example.successcontribution.screens.list_users

import com.example.successcontribution.model.response.UserRest
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.successcontribution.databinding.ListUsersBinding
import com.example.successcontribution.shared.Constant.ADMIN
import com.example.successcontribution.shared.Constant.EXCO
import com.example.successcontribution.shared.Constant.LOAN_CHECKER
import com.example.successcontribution.shared.Constant.PRESIDENT
import com.example.successcontribution.shared.Constant.SUPER_ADMIN


class ListUsersAdapter(private val onUserClickListener: (UserRest, Int) -> Unit) : ListAdapter<UserRest, ListUsersAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userRest = getItem(position)
        if (userRest != null) {
            if (EXCO == userRest.firstName || LOAN_CHECKER == userRest.firstName
                || PRESIDENT == userRest.firstName || ADMIN == userRest.firstName
                || SUPER_ADMIN == userRest.firstName
            ) {
                holder.cardHeader.visibility = View.GONE
            } else {
                holder.cardHeader.visibility = View.VISIBLE
                holder.bind(userRest)
                holder.itemView.setOnClickListener{
                    onUserClickListener.invoke(userRest, position)
                }
            }
        }
    }


    inner class ViewHolder(private val binding: ListUsersBinding) : RecyclerView.ViewHolder(binding.root) {

        private val name = binding.name
        private val department = binding.department
        val cardHeader = binding.cardHeader

        fun bind(userRest: UserRest) {
            val fullName = userRest.firstName + " " + userRest.lastName
            name.text = fullName
            department.text = userRest.department
        }
    }


    object DiffCallback: DiffUtil.ItemCallback<UserRest>() {
        override fun areItemsTheSame(oldItem: UserRest, newItem: UserRest): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserRest, newItem: UserRest): Boolean {
            return oldItem == newItem
        }

    }

}



