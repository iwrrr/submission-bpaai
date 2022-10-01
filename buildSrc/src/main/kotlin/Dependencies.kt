object AndroidProjectConfig {
    const val minSdk = 23
    const val compileSdk = 32
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "id.hwaryun.storygram"
}

object Libraries {

    object Versions {
        const val coreKtx = "1.8.0"
        const val appcompat = "1.4.2"
        const val constraintLayout = "2.1.4"
        const val activityKtx = "1.5.1"
        const val fragmentKtx = "1.5.2"
        const val navigationKtx = "2.5.1"
        const val lifecycle = "2.5.0"
        const val googleMaterial = "1.6.1"
        const val swipeRefresh = "1.1.0"
        const val gson = "2.9.0"
        const val coroutine = "1.6.1"
        const val retrofit = "2.9.0"
        const val httpLogging = "5.0.0-alpha.6"
        const val hilt = "2.42"
        const val chucker = "3.5.2"
        const val junit = "4.13.2"
        const val androidTestJunit = "1.1.3"
        const val espresso = "3.4.0"
        const val jetpackDatastore = "1.0.0"
        const val glide = "4.13.2"
        const val lottie = "5.2.0"
        const val paging = "3.1.1"
        const val room = "2.4.3"
        const val roomPaging = "2.4.3"
        const val recyclerView = "1.2.1"
        const val googleMaps = "18.1.0"
        const val googleLocation = "19.0.1"
    }

    const val androidxCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidxConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationKtx}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigationKtx}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleViewModelKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val googleAndroidMaterial =
        "com.google.android.material:material:${Versions.googleMaterial}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val coroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val httpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.httpLogging}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val chuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"
    const val junit = "junit:junit:${Versions.junit}"
    const val androidJunit = "androidx.test.ext:junit:${Versions.androidTestJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val jetpackDatastore =
        "androidx.datastore:datastore-preferences:${Versions.jetpackDatastore}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    const val pagingCommon = "androidx.paging:paging-common-ktx:${Versions.paging}"
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomPaging = "androidx.room:room-paging:${Versions.roomPaging}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val googleMaps = "com.google.android.gms:play-services-maps:${Versions.googleMaps}"
    const val googleLocation =
        "com.google.android.gms:play-services-location:${Versions.googleLocation}"
}