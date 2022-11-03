package com.example.surveyapp.presentation.add_poll.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapp.R

@Composable
fun CreatePollAlertDialog(dialogState: Boolean, code: String, onShare: () -> Unit, onDismiss: () -> Unit) {

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    if (dialogState) {
        AlertDialog(
            onDismissRequest = { },
            backgroundColor = MaterialTheme.colors.background,
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Survey Code", fontWeight = FontWeight.Bold,
                            fontSize = 24.sp, color = MaterialTheme.colors.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .background(MaterialTheme.colors.surface),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = code, fontSize = 32.sp,
                            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(MaterialTheme.colors.surface)
                                .padding(horizontal = 4.dp, vertical = 8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_copy_24),
                            contentDescription = "print",
                            modifier = Modifier.clickable {
                                clipboardManager.setText(AnnotatedString((code)))
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Do you want to share this survey with your friends?",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextButton(
                        onClick = { onShare() },
                        modifier = Modifier
                            .fillMaxWidth(0.75f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        Text(
                            "SHARE",
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = MaterialTheme.colors.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painterResource(id = R.drawable.ic__share),
                            contentDescription = "Share"
                        )
                    }
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                            .fillMaxWidth(0.75f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 10.dp,
                            pressedElevation = 15.dp,
                            disabledElevation = 0.dp
                        )
                    ) {
                        Text(text = "OK", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        )
    }
}