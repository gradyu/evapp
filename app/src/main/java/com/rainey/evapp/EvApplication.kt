/*
 * Copyright (C) 2018 grady
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")
package com.rainey.evapp

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import android.support.v4.app.Fragment
import com.rainey.evapp.common.Dispatch
import com.rainey.evapp.injection.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import java.util.*
import javax.inject.Inject
import kotlin.properties.Delegates

class EvApplication : Application(), HasActivityInjector, HasSupportFragmentInjector, ActivityLifecycleCallbacks {


    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var dispatch: Dispatch

    private val stack by lazy {
        Stack<Activity>()
    }

    private var activityCounter: Int by Delegates.observable(0) { _, old, new ->
        if (old == 0 && new == 1) {
            for (listener in dispatch.applicationStatusListeners) {
                listener.applicationBecomeForeground()
            }
        } else if (new == 0 && old == 1) {
            for (listener in dispatch.applicationStatusListeners) {
                listener.applicationBecomeBackground()
            }
        }
    }

    val currentActivity: Activity?
        get() {
            if (stack.isEmpty()) return null
            return stack.lastElement()
        }

    val appIsBackground: Boolean
        get() = activityCounter == 0

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
        registerActivityLifecycleCallbacks(this)
    }

    override fun onTerminate() {
        unregisterActivityLifecycleCallbacks(this)
        super.onTerminate()
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
        if (stack.isEmpty()) {
            dispatch.start()
        }
        stack.push(activity)
    }

    override fun onActivityStarted(activity: Activity?) {
        activityCounter++
    }

    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {

    }

    override fun onActivityResumed(activity: Activity?) {

    }

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityStopped(activity: Activity?) {
        activityCounter--
    }

    override fun onActivityDestroyed(activity: Activity?) {
        stack.remove(activity)
        if (stack.isEmpty()) {
            dispatch.stop()
        }
    }

}