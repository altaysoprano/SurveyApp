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
fun CreatePollAlertDialog(dialogState: Boolean, code: String, onDismiss: () -> Unit) {

    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    if (dialogState) {
        AlertDialog(
            onDismissRequest = { },
            backgroundColor = MaterialTheme.colors.background,
            text = {
                Column(){
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
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = code, fontSize = 32.sp,
                            fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.background,
                            modifier = Modifier.background(MaterialTheme.colors.onBackground).padding(4.dp)
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
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismiss() },
                        border = BorderStroke(1.dp, color = MaterialTheme.colors.onPrimary),
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        )
    }
}