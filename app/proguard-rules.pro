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
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-renamesourcefileattribute SourceFile

-repackageclasses ''

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses

-overloadaggressively

-ignorewarnings

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

-keepattributes *Annotation*

-keep class * extends androidx.lifecycle.ViewModel { <init>(); }
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * { *; }

-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class com.google.dagger.** { *; }
-keep interface dagger.hilt.** { *; }

-keep class org.apache.poi.** { *; }
-keep class org.openxmlformats.schemas.** { *; }
-keep class org.apache.xmlbeans.** { *; }
-keepnames class org.openxmlformats.schemas.** { *; }
-keep interface org.apache.poi.** { *; }
-keep class com.sun.xml.internal.stream.** { *; }
-dontwarn com.sun.xml.internal.stream.**
-dontwarn org.apache.poi.ooxml.**
-dontwarn org.apache.xmlbeans.**






