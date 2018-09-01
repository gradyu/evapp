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

object Const {
    /**        Common Config         **/
    const val SPLASH_DELAY = 3L
    const val APP_LOG_TAG  = "EvApplication"

    /**       Page Arouter Path      **/
    const val APP_HOME_PATH = "/app/home"

    /**     Service Arouter Path     **/
    const val SRV_LOGGER = "/service/logger"
    const val SRV_DEGRADE = "/service/degrade"
    const val SRV_PATH_REPLACE = "/service/path"
    const val SRV_JSON = "/service/json"
}