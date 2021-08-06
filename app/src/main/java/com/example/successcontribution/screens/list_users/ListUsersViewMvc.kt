package com.example.successcontribution.screens.list_users

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.successcontribution.databinding.ActivityListUsersBinding
import com.example.successcontribution.databinding.ListUsersBinding
import com.example.successcontribution.model.response.UserRest
import com.example.successcontribution.screens.common.viewmvc.BaseViewMvc
import com.example.successcontribution.shared.Constant

class ListUsersViewMvc(
    activity: Activity,
    parent: ViewGroup?
) : BaseViewMvc<ListUsersViewMvc.Listener>(activity) {

    interface Listener {
        fun swipeRefreshClicked()
        fun onUserClicked(clickedUser: UserRest, position: Int)
    }

    private val binding = ActivityListUsersBinding.inflate(activity.layoutInflater, parent, false)
    val rootView = binding.root

    private val recyclerView: RecyclerView = binding.recyclerview
    private val swipeRefresh: SwipeRefreshLayout = binding.swipe
    private var listUserAdapter: ListAdapter<UserRest, ListUsersAdapter.ViewHolder>

    init {
        hideOpeningKeyBoard(binding.searchUser)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        swipeRefresh.setOnRefreshListener {
            for (listener in listeners) {
                listener.swipeRefreshClicked()
            }
        }

        listUserAdapter = ListUsersAdapter { clickedUser, position ->
            for (listener in listeners) {
                listener.onUserClicked(clickedUser, position)
            }
        }

        recyclerView.adapter = listUserAdapter
    }

    fun bindUsers(users: List<UserRest>) {
        listUserAdapter.submitList(users)
    }

    fun showSwipeRefresh() {
        swipeRefresh.isRefreshing = true
    }

    fun hideSwipeRefresh() {
        if (swipeRefresh.isRefreshing)
            swipeRefresh.isRefreshing = false
    }


    class ListUsersAdapter(private val onUserClickListener: (UserRest, Int) -> Unit) :
        ListAdapter<UserRest, ListUsersAdapter.ViewHolder>(DiffCallback) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                ListUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val userRest = getItem(position)
            if (userRest != null) {
                if (Constant.EXCO == userRest.firstName || Constant.LOAN_CHECKER == userRest.firstName
                    || Constant.PRESIDENT == userRest.firstName || Constant.ADMIN == userRest.firstName
                    || Constant.SUPER_ADMIN == userRest.firstName
                ) {
                    holder.cardHeader.visibility = View.GONE
                } else {
                    holder.cardHeader.visibility = View.VISIBLE
                    holder.bind(userRest)
                    holder.itemView.setOnClickListener {
                        onUserClickListener.invoke(userRest, position)
                    }
                }
            }
        }

        inner class ViewHolder(private val binding: ListUsersBinding) :
            RecyclerView.ViewHolder(binding.root) {

            private val name = binding.name
            private val department = binding.department
            val cardHeader = binding.cardHeader

            fun bind(userRest: UserRest) {
                val fullName = userRest.firstName + " " + userRest.lastName
                name.text = fullName
                department.text = userRest.department
            }
        }

        object DiffCallback : DiffUtil.ItemCallback<UserRest>() {
            override fun areItemsTheSame(oldItem: UserRest, newItem: UserRest): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(oldItem: UserRest, newItem: UserRest): Boolean {
                return oldItem == newItem
            }

        }

    }

}