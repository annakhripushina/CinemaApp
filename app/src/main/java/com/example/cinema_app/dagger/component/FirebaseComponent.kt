package com.example.cinema_app.dagger.component

import com.example.cinema_app.service.FirebaseMessagingService
import dagger.Component
import javax.inject.Scope

@ServiceScope
@Component(dependencies = [AppComponent::class])
interface FirebaseComponent {
    fun inject(firebaseMessagingService: FirebaseMessagingService)
}

@Scope
annotation class ServiceScope