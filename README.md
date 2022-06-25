# The Movie Database

A simple app written using Kotlin and MVVM design pattern. The app shows infinite list of popular movies and their details retrieved from TMDb API.

## API Key
Add your api key in the [gradle][1] file:
```kotlin
buildTypes {
    ...
    debug {
        ...
        buildConfigField 'String', 'TMDB_API_KEY', '"YOUR_API_KEY"'
    }
}
```

## Libraries

- Coroutines and Flows
- Jetpack Compose
- Dagger 2
- Coil
- MockK
- Paging 3
- Retrofit
- Room


## Testing

The project contains  [Local unit tests][2] and [Instrumented tests][3].

Just run `./gradlew test` or `./gradlew connectedAndroidTest`

## Screenshots

<img width="30%" src="screenshots/phone_portrait_movies.png" /> <img width="30%" src="screenshots/phone_portrait_movie_details_1.png" /> <img width="30%" src="screenshots/phone_portrait_movie_details_2.png" />


<img width="60%" src="screenshots/phone_landscape_movies.png" />

<img width="60%" src="screenshots/tablet_portrait.png" />

<img width="90%" src="screenshots/tablet_landscape.png" />

[1]: app/build.gradle
[2]: app/src/test/java/com/example/themoviedatabase/
[3]: app/src/androidTest/java/com/example/themoviedatabase/
