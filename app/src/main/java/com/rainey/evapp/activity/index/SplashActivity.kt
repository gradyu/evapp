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

package com.rainey.evapp.activity.index

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.rainey.evapp.R
import com.rainey.evapp.activity.BaseActivity
import com.rainey.evapp.common.Const
import com.rainey.evapp.common.extension.plusAssign
import com.rainey.evapp.activity.main.MainActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        hideSystemUiNavigationBar()
        setupDelayHomeActivity()
    }

    private fun hideSystemUiNavigationBar() {
        if (Build.VERSION.SDK_INT < 19) {
            window.decorView.systemUiVisibility = View.GONE
        } else {
            val uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN
            window.decorView.systemUiVisibility = uiOption
        }
    }

    private fun setupDelayHomeActivity() {
        autoDisposables += Observable.timer(Const.SPLASH_DELAY, TimeUnit.SECONDS).subscribe {
            val intent = Intent(this@SplashActivity, MainActivity::class.java).apply {
                putExtra("name", "grady")
                putExtra("age", "19")
            }
            startActivity(intent)
            finish()
        }
    }

}
