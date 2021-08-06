package com.example.successcontribution.common.dependencyInjection

import com.example.successcontribution.network_usecase.AttemptLoginUseCase
import com.example.successcontribution.network_usecase.FetchUsersUseCase
import com.example.successcontribution.screens.common.ScreensNavigator
import com.example.successcontribution.screens.common.dialogs.DialogsNavigator
import com.example.successcontribution.screens.common.preferences.MySharedPreference
import com.example.successcontribution.screens.common.viewmvc.ViewMvcFactory
import java.lang.reflect.Field

class Injector(private val compositionRoot: PresentationCompositionRoot) {

    fun inject(client: Any) {
        for (field in getAllFields(client)) {
            if (isAnnotatedForInjection(field)) {
                injectField(client, field)
            }
        }
    }

    private fun getAllFields(client: Any): Array<out Field> {
        val clientClass = client::class.java
        return clientClass.declaredFields
    }

    private fun isAnnotatedForInjection(field: Field): Boolean {
        val fieldAnnotations = field.annotations
        for (annotation in fieldAnnotations) {
            if (annotation is Service) {
                return true
            }
        }
        return false
    }

    private fun injectField(client: Any, field: Field) {
        val isAccessibleInitially = field.isAccessible
        field.isAccessible = true
        field.set(client, getServiceForClass(field.type))
        field.isAccessible = isAccessibleInitially
    }

    private fun getServiceForClass(type: Class<*>): Any {
        when (type) {
            DialogsNavigator::class.java -> {
                return compositionRoot.dialogsNavigator
            }
            ScreensNavigator::class.java -> {
                return compositionRoot.screensNavigator
            }
            FetchUsersUseCase::class.java -> {
                return compositionRoot.fetchUsersUseCase
            }
            AttemptLoginUseCase::class.java -> {
                return compositionRoot.attemptLoginUseCase
            }
            ViewMvcFactory::class.java -> {
                return compositionRoot.viewMvcFactory
            }
            MySharedPreference::class.java -> {
                return compositionRoot.mySharedPreference
            }
            else -> {
                throw Exception("unsupported service type: $type")
            }
        }
    }
}