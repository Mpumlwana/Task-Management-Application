package com.example.taskmanagementapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskmanagementapp2.ui.theme.TaskManagementApp2Theme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagementApp2Theme {
                SettingsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("settings", ComponentActivity.MODE_PRIVATE) }

    var notificationSound by remember { mutableStateOf(sharedPreferences.getString("notification_sound", "Default") ?: "Default") }
    var notificationFrequency by remember { mutableStateOf(sharedPreferences.getString("notification_frequency", "Every Hour") ?: "Every Hour") }
    var enableNotifications by remember { mutableStateOf(sharedPreferences.getBoolean("enable_notifications", true)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.notification_settings),
                style = MaterialTheme.typography.headlineSmall
            )

            NotificationSoundDropdown(
                selectedSound = notificationSound,
                onSoundSelected = { notificationSound = it }
            )

            NotificationFrequencyDropdown(
                selectedFrequency = notificationFrequency,
                onFrequencySelected = { notificationFrequency = it }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.enable_notifications))
                Switch(
                    checked = enableNotifications,
                    onCheckedChange = { enableNotifications = it }
                )
            }

            Button(
                onClick = {
                    with(sharedPreferences.edit()) {
                        putString("notification_sound", notificationSound)
                        putString("notification_frequency", notificationFrequency)
                        putBoolean("enable_notifications", enableNotifications)
                        apply()
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.save_settings))
            }
        }
    }
}

@Composable
fun NotificationSoundDropdown(
    selectedSound: String,
    onSoundSelected: (String) -> Unit
) {
    val sounds = listOf("Default", "Chime", "Bell", "Chirp")
    DropdownSettingItem(
        label = stringResource(R.string.notification_sound),
        items = sounds,
        selectedItem = selectedSound,
        onItemSelected = onSoundSelected
    )
}

@Composable
fun NotificationFrequencyDropdown(
    selectedFrequency: String,
    onFrequencySelected: (String) -> Unit
) {
    val frequencies = listOf("Every Hour", "Every 3 Hours", "Every 6 Hours", "Daily")
    DropdownSettingItem(
        label = stringResource(R.string.notification_frequency),
        items = frequencies,
        selectedItem = selectedFrequency,
        onItemSelected = onFrequencySelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSettingItem(
    label: String,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(label)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}