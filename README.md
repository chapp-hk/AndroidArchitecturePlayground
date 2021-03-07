# [WIP]Weather App

A simple Android app that use https://openweathermap.org/current apis to fetch and display weather information

## Features

- Search by city name. The result of the search is to display the current weather information for the searched location.
- Search by GPS location
- When come back to the app after closing it, the weather for the most recent search is displayed.
- List of recently searched locations. Can tap on a recent search location and see the current weather location.
- Can delete one or more recently searched locations.

## Requirements

- Android 5.0 (API Level 21) or above
- Network connection
- GPS

## Environment Setting

If you want to use your own API key, please modify [buildSrc/src/main/java/Deps.kt](./buildSrc/src/main/java/Deps.kt)

## Permission

- `android.permission.INTERNET`: for fetching api
- `android.permission.ACCESS_FINE_LOCATION`: for getting current location

## Major Dependencies

- [Hilt](https://dagger.dev/hilt/)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- [Kotlinx Coroutines Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)
- [Retrofit](http://square.github.io/retrofit)
- [Data Binding](https://developer.android.com/topic/libraries/data-binding/index.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Android Play Services Location](https://developer.android.com/training/location)
- [MockK](https://mockk.io/)
- [Strikt](https://strikt.io/)
- [Espresso](https://developer.android.com/training/testing/espresso)

## App Architecture

Clean Architecture with MVVM is applied
- [https://levelup.gitconnected.com/clean-architecture-with-mvvm-34cc05ab3bc5](https://levelup.gitconnected.com/clean-architecture-with-mvvm-34cc05ab3bc5)

## Modules

- **app**: clean architecture presentation layer
- **data**: clean architecture data layer
- **domain**: clean architecture domain layer
- **base**: base module contains all base classes and utils
- **base-test**: base module for testing utils
- **mobile-service**: module contains mobile service e.g. Play Services Location

## Test

- Unit test
- UI test
