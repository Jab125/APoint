-libraryjars <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class)
#-keepattributes *Annotation*, Exceptions, Signature, Deprecated, SourceFile, SourceDir, LineNumberTable, LocalVariableTable, LocalVariableTypeTable, Synthetic, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations, RuntimeVisibleParameterAnnotations, RuntimeInvisibleParameterAnnotations, AnnotationDefault, InnerClasses
-keepattributes InnerClasses
-allowaccessmodification
-overloadaggressively
-dontusemixedcaseclassnames

-optimizationpasses 100

-flattenpackagehierarchy
-repackageclasses 'com.jab125.apoint'
-keep class com.jab125.apoint.api.** {
    public *;
}