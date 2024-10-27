package com.shivam_raj.noteapp.screens.createAccountOrLoginScreen

import android.util.Patterns
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shivam_raj.noteapp.R
import com.shivam_raj.noteapp.navigationGraph.Screens
import com.shivam_raj.noteapp.onlineDatabase.createAccount
import kotlinx.coroutines.launch

@Composable
fun CreateAccountPage(
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    val buttonEnabled by remember {
        derivedStateOf{
            Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length >= 6 && name.isNotBlank()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (!isSystemInDarkTheme()) {
                    Modifier.designedBackground()
                } else {
                    Modifier.background(MaterialTheme.colorScheme.background)
                }
            )
            .systemBarsPadding()
    ) {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onBackground) {
            Column(
                Modifier.systemBarsPadding().padding(5.dp)
            ) {
                IconButton(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background.copy(0.5f),
                        RoundedCornerShape(12.dp)
                    ),
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    onClick = remember {
                        {
                            navController.popBackStack()
                        }
                    }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Go back")
                }
                Spacer(Modifier.fillMaxHeight(0.03f))
                Text(
                    "Create\nAccount :)",
                    fontFamily = FontFamily.Serif,
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(start = 19.dp)
                )
                Spacer(Modifier.fillMaxHeight(0.06f))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .shadow(5.dp, MaterialTheme.shapes.medium),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium,
                        placeholder = {
                            Text("Full name")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = remember {
                            {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        }),
                        enabled = !isLoading
                    )
                    Spacer(Modifier.height(25.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .shadow(5.dp, MaterialTheme.shapes.medium),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium,
                        placeholder = {
                            Text("Email")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(onNext = remember {
                            {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        }),
                        enabled = !isLoading
                    )
                    Spacer(Modifier.height(25.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .shadow(5.dp, MaterialTheme.shapes.medium),
                        colors = TextFieldDefaults.colors(
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceBright,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = MaterialTheme.shapes.medium,
                        placeholder = {
                            Text("Password")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = remember {
                            {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                        }),
                        enabled = !isLoading,
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation('*'),
                        trailingIcon = {
                            IconButton(onClick = {showPassword=!showPassword}) {
                                Icon(
                                    painter = rememberVectorPainter(ImageVector.vectorResource(if (showPassword) R.drawable.password_visibility_off else R.drawable.password_visible)),
                                    contentDescription = "Hide/UnHide password"
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(25.dp))
                    AnimatedContent(
                        targetState = isLoading,
                        label = "",
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        }
                    ) {
                        if (it) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .background(Color(0xff9a6aff), CircleShape)
                                    .padding(8.dp),
                                color = Color(0xfff4eeff),
                                strokeWidth = 3.dp
                            )
                        } else {
                            ElevatedButton(
                                enabled = buttonEnabled,
                                onClick = remember {
                                    {
                                        isLoading = true
                                        coroutineScope.launch {
                                            val error = createAccount(email, password, name)
                                            isLoading = false
                                            if (error == null) {
                                                navController.navigate(Screens.NoteListScreen.route) {
                                                    popUpTo(Screens.CreateAccountScreen.route) {
                                                        inclusive = true
                                                    }
                                                }
                                            } else {
                                                snackBarHostState.showSnackbar(
                                                    message = error,
                                                    withDismissAction = true,
                                                    duration = SnackbarDuration.Short
                                                )
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xff9a6aff),
                                    contentColor = Color(0xfff4eeff)
                                ),
                                modifier = Modifier.fillMaxWidth(0.4f)
                            ) {
                                Text("Sign up")
                            }
                        }
                    }
                    Spacer(Modifier.height(25.dp))
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    TextButton(
                        onClick = remember {
                            {
                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
                    ) {
                        Text(text = buildAnnotatedString {
                            append("Already have an account? ")
                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("Log in")
                            }
                        }, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        SnackbarHost(snackBarHostState)
    }
}

private fun Modifier.designedBackground(): Modifier {
    return this
        .background(Color.White)
        .background(
            Brush.linearGradient(
                listOf(
                    Color(0xFFF44336),
                    Color(0xFFFF9800),
                    Color(0xFFFFEB3B),
                    Color(0xFF4CAF50),
                    Color(0xFF2196F3)
                )
            ),
            alpha = 0.25f
        )
}