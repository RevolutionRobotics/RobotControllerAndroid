# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

-keepattributes *Annotation*
-keep public class * extends java.lang.Exception

# KOTLIN
-keepnames class kotlinx.** { *; }
-keep class kotlin.Metadata { *; }
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.atomicfu.**
-dontwarn kotlin.**

# JETPACK
-keep class * implements android.arch.lifecycle.LifecycleObserver {
    <init>(...);
}
-keepclassmembers class * extends android.arch.lifecycle.ViewModel {
    <init>(...);
}
-keepclassmembers class android.arch.lifecycle.Lifecycle$State { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event { *; }
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

# DATA BINDING
-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

# GSON
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# GLIDE
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# CRASHLYTICS
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**

# APP SPECIFIC
-keep public class * extends com.revolution.robotics.BaseFragment
-keep public class com.revolution.robotics.core.domain.** {
  *;
}

# OTHER
-dontwarn com.google.errorprone.annotations.*
