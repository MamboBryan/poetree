object Libraries {

    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}" }
    val core by lazy { "androidx.core:core-ktx:${Versions.ktx}" }

    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
    val material3Design by lazy { "androidx.compose.material3:material3:${Versions.material3}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val fragment by lazy { "androidx.fragment:fragment-ktx:1.5.2" }
    val recyclerview by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerview}" }
    val legacySupport by lazy { "androidx.legacy:legacy-support-v4:${Versions.legacy}" }
    val playServicesAuth by lazy { "com.google.android.gms:play-services-auth:${Versions.playServicesAuth}" }
    val playCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.playCoroutines}" }
    val support by lazy { "com.android.support:design:${Versions.support}" }

    val junit by lazy { "junit:junit:${Versions.jUnit}" }

    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.datastore}" }
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }
    val navigationCommon by lazy { "androidx.navigation:navigation-common-ktx:${Versions.navigation}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
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

    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }
    val coil by lazy { "io.coil-kt:coil:${Versions.coil}" }
    val wysiwyg by lazy { "com.github.onecode369:WYSIWYG:${Versions.wysiwyg}" }
    val nativeEditor by lazy { "com.github.irshulx:laser-native-editor:${Versions.nativeEditor}" }
    val richEditor by lazy { "jp.wasabeef:richeditor-android:${Versions.richEditor}" }
    val prettyTime by lazy { "org.ocpsoft.prettytime:prettytime:${Versions.prettyTime}" }
    val delegate by lazy { "com.github.Zhuinden:fragmentviewbindingdelegate-kt:${Versions.delegate}" }
    val switch by lazy { "com.github.zcweng:switch-button:${Versions.switch}" }
    val circularImage by lazy { "com.mikhaellopez:circularimageview:${Versions.circularImageView}" }
    val liquidSwipe by lazy { "com.cuberto:liquid-swipe:${Versions.liquidSwipe}" }
    val sneaker by lazy { "com.irozon.sneaker:sneaker:${Versions.sneaker}" }
    val alert by lazy { "com.irozon.alertview:alertview:${Versions.alert}" }
    val alerter by lazy { "com.github.tapadoo:alerter:${Versions.alerter}" }
    val likeButton by lazy { "com.github.jd-alexander:LikeButton:${Versions.likeButton}" }
    val scrollLayout by lazy { "com.github.w446108264:ScrollableLayout:${Versions.scrollLayout}" }
    val motionToast by lazy { "com.github.Spikeysanju:MotionToast:${Versions.motionToast}" }
    val cascade by lazy { "me.saket.cascade:cascade:1.3.0" }

    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }

    val okHttp by lazy { "com.squareup.okhttp3:okhttp" }
    val okHttpLogging by lazy { "com.squareup.okhttp3:logging-interceptor" }

}