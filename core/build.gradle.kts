plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = AndroidProjectConfig.compileSdk

    defaultConfig {
        minSdk = AndroidProjectConfig.minSdk
        targetSdk = AndroidProjectConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    flavorDimensions += listOf("default")

    productFlavors {
        create("production") {
            dimension = "default"
            buildConfigField(
                "String",
                "APPLICATION_ID",
                "\"${AndroidProjectConfig.applicationId}\""
            )
        }
    }
}

dependencies {

    api(Libraries.androidxCoreKtx)
    api(Libraries.androidxAppcompat)
    api(Libraries.googleAndroidMaterial)
    api(Libraries.androidxConstraintLayout)
    api(Libraries.activityKtx)
    api(Libraries.fragmentKtx)

    testApi(Libraries.junit)
    androidTestApi(Libraries.androidJunit)
    androidTestApi(Libraries.espressoCore)

    api(Libraries.lifecycleViewModelKtx)
    api(Libraries.lifecycleLiveDataKtx)
    api(Libraries.lifecycleRuntimeKtx)

    api(Libraries.retrofitConverterGson)
    api(Libraries.retrofit2)
    api(Libraries.httpLogging)

    api(Libraries.room)
    kapt(Libraries.roomCompiler)
    api(Libraries.paging)
    api(Libraries.roomPaging)

    api(Libraries.coroutineCore)
    api(Libraries.coroutineAndroid)

    api(Libraries.jetpackDatastore)

    api(Libraries.gson)

    api(Libraries.glide)

    api(project(":style"))
}