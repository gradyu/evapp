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

package com.rainey.evapp.activity.main

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.orhanobut.logger.Logger
import com.rainey.evapp.R
import com.rainey.evapp.activity.BaseActivity
import com.rainey.evapp.common.delegate.extra
import com.rainey.evapp.common.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_PWD = "password"
        const val EXTRA_AGE = "age"
        const val EXTRA_MARRIED = "married"
        const val EXTRA_WEIGHT = "weight"
    }

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var injectName: String

    @Inject
    lateinit var helper: SharedPreferencesHelper

    private val name: String? by extra(EXTRA_NAME)
    private val password: String? by extra(EXTRA_PWD)
    private val age: Int? by extra(EXTRA_AGE)
    private val married: Boolean? by extra(EXTRA_MARRIED)
    private val weight: Float? by extra(EXTRA_WEIGHT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        with(helper) {
            this.name = this@MainActivity.name ?: ""
            this.password = this@MainActivity.password ?: ""
            this.age = this@MainActivity.age ?: 10
            this.married = this@MainActivity.married ?: false
            this.weight = this@MainActivity.weight ?: 60.0f
        }

        with(helper) {
            Logger.d("name: $name")
            Logger.d("password: $password")
            Logger.d("age: $age")
            Logger.d("married: $married")
            Logger.d("weight: $weight")
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "InjectName: $injectName\nContext: $context", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
