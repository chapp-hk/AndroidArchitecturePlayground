package app.ch.domain.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class CoroutineDispatcherProvider @Inject
constructor() {

    val ioDispatcher: CoroutineDispatcher get() = Dispatchers.IO
}
