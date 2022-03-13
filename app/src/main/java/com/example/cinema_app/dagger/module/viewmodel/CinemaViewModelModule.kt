package com.example.cinema_app.dagger.module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cinema_app.presentation.view.cinemaList.CinemaListViewModel
import com.example.cinema_app.presentation.view.detail.CinemaDetailViewModel
import com.example.cinema_app.presentation.view.favourite.FavouriteViewModel
import com.example.cinema_app.presentation.view.shedule.ScheduleViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class CinemaViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CinemaListViewModel::class)
    internal abstract fun bindOverviewViewModel(viewModel: CinemaListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CinemaDetailViewModel::class)
    internal abstract fun bindDetailViewModel(viewModel: CinemaDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    internal abstract fun bindNewsViewModel(viewModel: FavouriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    internal abstract fun bindListViewModel(viewModel: ScheduleViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: CinemaViewModelFactory): ViewModelProvider.Factory

}