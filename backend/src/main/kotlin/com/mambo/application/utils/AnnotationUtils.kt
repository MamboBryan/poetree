package com.mambo.application.utils

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude

class AnnotationExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        return f?.getAnnotation(Exclude::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }
}