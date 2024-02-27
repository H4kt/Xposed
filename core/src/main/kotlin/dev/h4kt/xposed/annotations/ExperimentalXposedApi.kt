package dev.h4kt.xposed.annotations

@RequiresOptIn("This API is experimental and might change in near fututre", level = RequiresOptIn.Level.ERROR)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class ExperimentalXposedApi
