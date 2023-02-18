object Libraries {

    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}" }
    val core by lazy { "androidx.core:core-ktx:${Versions.ktx}" }

    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val material3Design by lazy { "androidx.compose.material3:material3:${Versions.material3}" }
    val playServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}" }
    val playCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.playCoroutines}" }

    val junit by lazy { "junit:junit:${Versions.jUnit}" }

    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.datastore}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val viewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}" }
    val liveData by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}" }
    val savedState by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}" }
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}" }
    val lifecycleExtensions by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}" }
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val room by lazy { "androidx.room:room-ktx:${Versions.room}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.room}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.room}" }
    val paging by lazy { "androidx.paging:paging-runtime:${Versions.paging}" }
    val recyclerviewSelection by lazy { "androidx.recyclerview:recyclerview-selection:${Versions.recyclerviewSelection}" }
    val hiltWork by lazy { "androidx.hilt:hilt-work:${Versions.hiltWork}" }
    val hiltWorkCompiler by lazy { "androidx.hilt:hilt-compiler:${Versions.hiltWork}" }
    val workManager by lazy { "androidx.work:work-runtime-ktx:${Versions.workManager}" }
    val androidXStartup by lazy { "androidx.startup:startup-runtime:${Versions.androidXStartup}" }
    val splashScreen by lazy { "androidx.core:core-splashscreen:1.0.0" }

    val prettyTime by lazy { "org.ocpsoft.prettytime:prettytime:${Versions.prettyTime}" }

    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }

}