package com.fontaipi.riianthai.datastore

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val riianThaiDispatcher: RiianThaiDispatchers)

enum class RiianThaiDispatchers {
    Default,
    IO,
}