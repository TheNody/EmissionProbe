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

# Сохранение необходимых классов и методов для шифрования
-keep class javax.crypto.** { *; }
-keep class android.util.Base64 { *; }

# Обфускация всех остальных классов и методов
-keep class !com.calculation.** { *; }
-keep class !androidx.** { *; }
-keep class !android.** { *; }

# Обфускация CalculationViewModel
-keep class !com.calculation.tipcalculation.viewmodel.CalculationViewModel {
    <fields>;
    <methods>;
}

# Сохранение атрибутов, необходимых для дебага
-keepattributes SourceFile,LineNumberTable

# Скрытие оригинальных имен файлов исходного кода
-renamesourcefileattribute SourceFile

# Прочие настройки и правила
-dontwarn android.support.**
-dontwarn javax.annotation.**
-dontwarn kotlin.**
-ignorewarnings

# Обфускация строковых ресурсов
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** e(...);
    public static *** i(...);
    public static *** w(...);
    public static *** v(...);
}

# Сохранение классов и методов, используемых в XML
-keepclassmembers class * {
    @android.view.ViewDebug$ExportedProperty *;
    @android.view.ViewDebug$CapturedViewProperty *;
}

# Сохранение классов с аннотациями
-keepattributes *Annotation*

# Сохранение методов, использующихся в нативном коде
-keepclasseswithmembernames class * {
    native <methods>;
}

# Сохранение классов для сериализации
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Сохранение методов, используемых в Java-коде через рефлексию
-keepclassmembers class * {
    public <methods>;
}

# Сохранение классов, используемых в XML разметке
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Прочие настройки для повышения производительности
-optimizationpasses 5
-overloadaggressively
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-mergeinterfacesaggressively