package com.example.android.githubsearchwithsettings.ui

import android.os.Bundle
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.android.githubsearchwithsettings.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val languagePref: MultiSelectListPreference? = findPreference(
            getString(R.string.pref_language_key)
        )

        languagePref?.summaryProvider = Preference.SummaryProvider<MultiSelectListPreference> {
            val n = it.values.size
            if (n == 0) {
                getString(R.string.pref_language_not_set)
            } else {
                resources.getQuantityString(R.plurals.pref_language_summary, n, n)
            }
        }
    }
}