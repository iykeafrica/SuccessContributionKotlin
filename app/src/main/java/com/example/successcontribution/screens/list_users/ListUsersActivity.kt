package com.example.successcontribution.screens.list_users

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.successcontribution.common.dependencyInjection.Service
import com.example.successcontribution.model.response.UserRest
import com.example.successcontribution.network_usecase.FetchUsersUseCase
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.activity.BaseActivity
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.screens.common.preferences.Pref
import com.example.successcontribution.screens.dashboard.DashBoardActivity
import com.example.successcontribution.shared.Constant.AUTHORIZATION_TOKEN_DEFAULT_KEY
import kotlinx.coroutines.*

class ListUsersActivity : BaseActivity(), ListUsersViewMvc.Listener {

    private val coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var listUsersViewMvc: ListUsersViewMvc

    @field:Service private lateinit var fetchUsersUseCase: FetchUsersUseCase
    @field:Service private lateinit var mySharedPreference: MySharedPreference
    @field:Service private lateinit var dialogsNavigator: DialogsNavigator
    @field:Service private lateinit var screensNavigator: ScreensNavigator

    private var isDataLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injector.inject(this)
        listUsersViewMvc = compositionRoot.viewMvcFactory.newListUsersViewMvcFactory(null)
        setContentView(listUsersViewMvc.rootView)
    }

    override fun swipeRefreshClicked() {
        fetchUsers()
    }

    private fun fetchUsers() {
        val pref = mySharedPreference.preference
        val authorization = Pref.getStoredString(pref, AUTHORIZATION_TOKEN_DEFAULT_KEY, "")

        coroutineScope.launch {
                listUsersViewMvc.showProgressIndication()

            try {
                when (val result = fetchUsersUseCase.fetchUsers(authorization, 1, 20)) {
                    is FetchUsersUseCase.Result.Success -> {
                        onFetchSuccess(result.users)
                    }
                    is FetchUsersUseCase.Result.Failure -> {
                        onFetchFail()
                    }
                }
            } finally {
                listUsersViewMvc.hideProgressIndication()
                listUsersViewMvc.hideSwipeRefresh()
            }
        }
    }

    private fun onFetchSuccess(users: List<UserRest>) {
        listUsersViewMvc.bindUsers(users)
    }

    private fun onFetchFail() {
        dialogsNavigator.showServerErrorDialog()
    }

    override fun onUserClicked(clickedUser: UserRest, position: Int) {
        Toast.makeText(this, "${clickedUser.firstName} is in position $position", Toast.LENGTH_LONG)
            .show()
    }

    override fun onStart() {
        listUsersViewMvc.registerListener(this)
        super.onStart()
        if (!isDataLoaded)
            fetchUsers()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }
}