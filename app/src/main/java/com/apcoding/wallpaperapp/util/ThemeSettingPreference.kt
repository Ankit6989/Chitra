package com.apcoding.wallpaperapp.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingPreference @Inject constructor(
    @ApplicationContext context: Context
) : ThemeSetting {

    override val themeStream: MutableStateFlow<WallPaperTheme>
    override var theme: WallPaperTheme by AppThemePreferenceDelegate(
        "app_theme",
        WallPaperTheme.MODE_DAY
    )

    private val preferences: SharedPreferences =
        context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)

    init {
        themeStream = MutableStateFlow(theme)
    }

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: WallPaperTheme
    ) : ReadWriteProperty<Any?, WallPaperTheme> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): WallPaperTheme =
            WallPaperTheme.fromOrdinal(preferences.getInt(name, default.ordinal))

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: WallPaperTheme) {
            themeStream.value = value
            preferences.edit {
                putInt(name, value.ordinal)
            }
        }

    }
}