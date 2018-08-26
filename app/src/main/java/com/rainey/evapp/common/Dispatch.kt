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

package com.rainey.evapp.common


import com.orhanobut.logger.Logger
import com.rainey.evapp.common.config.LoggerConfig
import com.rainey.evapp.common.listener.ApplicationStatusListener
import javax.inject.Inject

interface Dispatch {

    val applicationStatusListeners: List<ApplicationStatusListener>

    /**
     * dispatch start
     */
    fun start()

    /**
     * dispatch stop
     */
    fun stop()
}

class DefaultDispatch @Inject constructor() : Dispatch {

    override val applicationStatusListeners: List<ApplicationStatusListener> by lazy {
        arrayListOf<ApplicationStatusListener>()
    }

    @Inject
    lateinit var loggerConfig: LoggerConfig

    override fun start() {
        loggerConfig.init()
        Logger.d("Dispatch start")

    }

    override fun stop() {
        Logger.d("Dispatch stop")
    }

}