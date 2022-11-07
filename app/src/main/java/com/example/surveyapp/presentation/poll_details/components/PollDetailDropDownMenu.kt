package com.example.surveyapp.presentation.poll_details.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.surveyapp.R

@Composable
fun PollDetailDropDownMenu(
    onDeleteItemClick: () -> Unit,
    onShareItemClick: () -> Unit,
    isOwner: Boolean
) {

    var showMenu by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        IconButton(onClick = { showMenu = !showMenu }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_more_vert_24),
                contentDescription = "Dropdown Menu Icon"
            )
        }
        DropdownMenu(
            expanded = showMenu, onDismissRequest = { showMenu = false },
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxWidth(0.4f)
        ) {
            if(isOwner) {
                DropdownMenuItem(
                    onClick = { onDeleteItemClick() },
                    modifier = Modifier.border(1.dp, MaterialTheme.colors.surface)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "Delete",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.error
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painterResource(id = R.drawable.ic_baseline_delete_24),
                            contentDescription = "Delete Survey",
                            tint = MaterialTheme.colors.error
                        )
                    }
                }
            }
            DropdownMenuItem(onClick = { onShareItemClick() }, modifier = Modifier.border(1.dp, MaterialTheme.colors.surface)) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Share", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painterResource(id = R.drawable.ic__share),
                        contentDescription = "Share"
                    )
                }
            }
        }
    }
}