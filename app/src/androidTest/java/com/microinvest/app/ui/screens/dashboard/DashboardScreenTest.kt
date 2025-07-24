package com.microinvest.app.ui.screens.dashboard

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microinvest.app.ui.theme.MicroInvestTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboardScreen_displaysCorrectly() {
        composeTestRule.setContent {
            MicroInvestTheme {
                // Mock ViewModel would be injected here in a real test
                // DashboardScreen()
            }
        }

        // Verify main elements are displayed
        composeTestRule.onNodeWithText("Dashboard").assertIsDisplayed()
    }

    @Test
    fun spareChangeCard_displaysCorrectAmount() {
        val testAmount = 25.75

        composeTestRule.setContent {
            MicroInvestTheme {
                SpareChangeCard(
                    availableSpareChange = testAmount,
                    onInvestSpareChange = { }
                )
            }
        }

        composeTestRule.onNodeWithText("Available Spare Change").assertIsDisplayed()
        composeTestRule.onNodeWithText("$25.75").assertIsDisplayed()
        composeTestRule.onNodeWithText("Invest Spare Change").assertIsDisplayed()
    }

    @Test
    fun investButton_isEnabledWhenSpareChangeAvailable() {
        composeTestRule.setContent {
            MicroInvestTheme {
                SpareChangeCard(
                    availableSpareChange = 10.0,
                    onInvestSpareChange = { }
                )
            }
        }

        composeTestRule.onNodeWithText("Invest Spare Change").assertIsEnabled()
    }

    @Test
    fun investButton_isDisabledWhenNoSpareChange() {
        composeTestRule.setContent {
            MicroInvestTheme {
                SpareChangeCard(
                    availableSpareChange = 0.0,
                    onInvestSpareChange = { }
                )
            }
        }

        composeTestRule.onNodeWithText("Invest Spare Change").assertIsNotEnabled()
    }
}