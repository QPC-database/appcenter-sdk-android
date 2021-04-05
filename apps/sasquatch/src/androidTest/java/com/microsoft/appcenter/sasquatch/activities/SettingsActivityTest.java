/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.appcenter.sasquatch.activities;

import android.content.Context;
import android.content.Intent;
import androidx.test.espresso.DataInteraction;
import androidx.test.rule.ActivityTestRule;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;
import com.microsoft.appcenter.distribute.Distribute;
import com.microsoft.appcenter.sasquatch.R;
import com.microsoft.appcenter.utils.storage.SharedPreferencesManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.PreferenceMatchers.withKey;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@SuppressWarnings("unused")
public class SettingsActivityTest {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    private Context mContext;

    @Before
    public void setUp() {
        mContext = getInstrumentation().getTargetContext();

        /* Clear preferences. */
        SharedPreferencesManager.initialize(mContext);
        SharedPreferencesManager.clear();

        /* Launch main activity and go to setting page. Required to properly initialize. */
        mActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.action_settings)).perform(click());
    }

    @Test
    public void testInitialState() {
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());
    }

    @Test
    public void testEnableAppCenter() {

        /* Disable App Center. */
        onCheckbox(R.string.appcenter_state_key).perform(click());

        /* Check App Center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Distribute.isEnabled().get());

        /* Unable enable analytics when app center is disabled. */
        onCheckbox(R.string.appcenter_analytics_state_key).perform(click());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Analytics.isEnabled().get());

        /* Unable enable crashes when app center is disabled. */
        onCheckbox(R.string.appcenter_crashes_state_key).perform(click());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Crashes.isEnabled().get());

        /* Unable enable distribute when app center is disabled. */
        onCheckbox(R.string.appcenter_distribute_state_key).perform(click());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Distribute.isEnabled().get());

        /* Enable app center. */
        onCheckbox(R.string.appcenter_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());
    }

    @Test
    public void testEnableAnalytics() {

        /* Disable analytics service. */
        onCheckbox(R.string.appcenter_analytics_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());

        /* Enable analytics service. */
        onCheckbox(R.string.appcenter_analytics_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());
    }

    @Test
    public void testEnableCrashes() {

        /* Disable distribute service. */
        onCheckbox(R.string.appcenter_crashes_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());

        /* Enable distribute service. */
        onCheckbox(R.string.appcenter_crashes_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());
    }

    @Test
    public void testEnableDistribute() {

        /* Disable distribute service. */
        onCheckbox(R.string.appcenter_distribute_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isNotChecked()));
        Assert.assertFalse(Distribute.isEnabled().get());

        /* Enable distribute service. */
        onCheckbox(R.string.appcenter_distribute_state_key).perform(click());

        /* Check app center and services state. */
        onCheckbox(R.string.appcenter_state_key).check(matches(isChecked()));
        Assert.assertTrue(AppCenter.isEnabled().get());
        onCheckbox(R.string.appcenter_analytics_state_key).check(matches(isChecked()));
        Assert.assertTrue(Analytics.isEnabled().get());
        onCheckbox(R.string.appcenter_crashes_state_key).check(matches(isChecked()));
        Assert.assertTrue(Crashes.isEnabled().get());
        onCheckbox(R.string.appcenter_distribute_state_key).check(matches(isChecked()));
        Assert.assertTrue(Distribute.isEnabled().get());
    }

    private DataInteraction onCheckbox(int id) {
        return onData(withKey(mContext.getString(id))).onChildView(withId(android.R.id.checkbox));
    }
}