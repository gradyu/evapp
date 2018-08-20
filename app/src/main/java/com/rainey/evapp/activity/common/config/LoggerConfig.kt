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

package com.rainey.evapp.activity.common.config

import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.rainey.evapp.BuildConfig
import com.rainey.evapp.activity.common.Const
import javax.inject.Inject

class LoggerConfig @Inject constructor() {

    private var initialized = false

    fun init() {
        if (initialized) return
        val format = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(1)
                .logStrategy(LogCatStrategy())
                .tag(Const.APP_LOG_TAG)
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(format) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        initialized = true
    }

    class LogCatStrategy : LogStrategy {

        private var last = -1

        override fun log(priority: Int, tag: String?, message: String) {
            Log.println(priority, "${randomKey()}$tag", message)
        }

        private fun randomKey(): String {
            var random = 10 * Math.random().toInt()
            if (random == last) {
                random = (random + 1) % 10
            }
            last = random
            return random.toString()
        }

    }
}