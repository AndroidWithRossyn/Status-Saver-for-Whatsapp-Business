-keepnames class androidx.appcompat.app.AppCompatViewInflater
-keep class androidx.appcompat.widget.SearchView { <init>(...); }

-keep class com.firebase.** { *; }
-keep class org.apache.** { *; }

# Only necessary if you downloaded the SDK jar directly instead of from maven.
-keep class com.shaded.fasterxml.jackson.** { *; }

# Add this global rule
-keepattributes Signature

-keep public class com.google.android.gms.ads.**{
   public *;
}

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


-dontwarn com.onesignal.**

# These 2 methods are called with reflection.
-keep class com.google.android.gms.common.api.GoogleApiClient {
    void connect();
    void disconnect();
}
-keep class com.onesignal.ActivityLifecycleListenerCompat** {*;}

