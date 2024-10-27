package com.shivam_raj.noteapp.screens.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController
) {
    val currentUser = Firebase.auth.currentUser
    var name by remember {
        mutableStateOf(currentUser?.displayName ?: "")
    }
    var email by remember {
        mutableStateOf(currentUser?.email ?: "")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Profile") },
                navigationIcon = {
                    IconButton(onClick = remember { { navController.popBackStack() } }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserProfileSection()
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedCard(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 12.dp),
                    text = "Your name",
                    style = MaterialTheme.typography.titleLarge ,
                    fontFamily = FontFamily.Serif
                )
                OutlinedTextField(
                    modifier = Modifier.padding(start = 8.dp, top = 6.dp, bottom = 12.dp),
                    value = name,
                    onValueChange = { name = it }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedCard(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp, top = 12.dp),
                    text = "Your email",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Serif
                )
                OutlinedTextField(
                    modifier = Modifier.padding(start = 8.dp, top = 6.dp, bottom = 12.dp),
                    value = email,
                    onValueChange = { email = it }
                )
            }

        }
    }
}
