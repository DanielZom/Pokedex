# Pokedex

Pokedex is an application where you can check out all of the existing Pokemons, and see their characteristic and some images of them.

## Development guidelines 
https://epam-my.sharepoint.com/:w:/p/daniel_zombori/EdVAiJhCUiBAq27_qZltMIEBzOp5J60v3Yfs0tl45Szytw?e=pH8Q5f

Guidelines  | Status
------------- | -------------
Base functionallity (list, details, prescribed information about Pokemons) | Done
Min. SDK 21 | Done
Use Kotlin language | Done
Use Clean Architecture (Repository pattern) and MVI (e.g. Uniflow lib) | Done (not with Uniflow)
Use JetPack: (ViewModel, Room, Navigation) | Done
Use Koin (DI) | Done
Use Retrofit2 and OkHttp3 | Done
Use Coroutines + Flow | Done
Use Moshi and Glide | Done
Create pagination with Jetpack’s Paging library | Done (with Paging 3)
Publish code in a github public repository | Done

Bonus tasks  | Status
------------- | -------------
Make app work offline too | Done
Write Unit Tests | Not made
Customize the project with something you believe could be useful for the app | Splash screen with animation

## How could it be better?
If I had more time I would develop these features:

* Navigation animation between list and detail navigation (like slide)
* A more detailed detail screen with more information, like location, more information about abilities
* An image gallery where I can check the forms of pokemon and some other cool images of them

## Dependecies what I used:
**The versions are separated in a version.gradle file for transparency.**

    //Default android dependencies - These are required for basic app development and for follow material design guidelines (like ConstraintLayout and Recyclerview)
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation "androidx.core:core-ktx:$kotlinCoreVersion"
    implementation "androidx.appcompat:appcompat:$appCompatVersion"
    implementation "com.google.android.material:material:$googleMaterialVersion"
    implementation "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    implementation "androidx.recyclerview:recyclerview:$recyclerViewVersion"

    //Test - I left these default generated dependencies to be able to write unit and UI tests in the future
    testImplementation "junit:junit:$junitVersion"
    androidTestImplementation "androidx.test.ext:junit:$androidxJunitVersion"
    androidTestImplementation "androidx.test.espresso:espresso-core:$espressoVersion"

    //Koin - Dependency injection library
    implementation "org.koin:koin-android:$koinVersion"
    implementation "org.koin:koin-androidx-viewmodel:$koinVersion"
    testImplementation "org.koin:koin-test:$koinVersion"

    //Retrofit - Network service library
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-moshi:$moshiConverterVersion"

    //Moshi - JSON parser library
    implementation "com.squareup.moshi:moshi:$moshiVersion"
    implementation "com.squareup.moshi:moshi-kotlin:$moshiKotlinVersion"
    kapt "com.squareup.moshi:moshi-kotlin-codegen:$moshiCodegenVersion"

    //Okhttp - Network client library
    //noinspection GradleDependency
    implementation "com.squareup.okhttp3:okhttp:$okhttpVersion"
    implementation "com.squareup.okhttp3:okhttp-urlconnection:$okhttpVersion"

    //Stetho - An easier way to debug android application throught chrome inspect (network calls details, database browser etc...)
    implementation "com.facebook.stetho:stetho:$stethoVersion"
    implementation "com.facebook.stetho:stetho-okhttp3:$stethoNetworkVersion"

    //Timber - A simple logger library
    implementation "com.jakewharton.timber:timber:$timberVersion"

    //Glide - Image fetcher library
    implementation ("com.github.bumptech.glide:glide:$glideVersion") {
        exclude group: "com.android.support"
    }
    kapt "com.github.bumptech.glide:compiler:$glideVersion"
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshVersion" //for CircularProgressDrawable to indicate loading in Glide

    //Navigation - Android Jetpack Fragment,Activity navigation simplifier library
    implementation "androidx.navigation:navigation-fragment-ktx:$navVersion"
    implementation "androidx.navigation:navigation-ui-ktx:$navVersion"

    //Room - Android Jetpack database manager
    implementation "androidx.room:room-runtime:$roomVersion"
    kapt "androidx.room:room-compiler:$roomVersion"
    implementation "androidx.room:room-ktx:$roomVersion"

    //Kotlin coroutines - Thread manager library
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesAndroidVersion"

    //Paging 3 - Pagination libary
    implementation "androidx.paging:paging-runtime-ktx:$pagingVersion"

    //Appcenter - Application crash and event tracker library
    implementation "com.microsoft.appcenter:appcenter-analytics:${appCenterSdkVersion}"
    implementation "com.microsoft.appcenter:appcenter-crashes:${appCenterSdkVersion}"
    
## Contributing
This is a introduction application, likely there won't be any future development.

## License
There is no license for this application.
