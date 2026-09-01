package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculadoraScreen()
                }
            }
        }
    }
}

@Composable
fun CalculadoraScreen() {
    var displayText by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var isNewOp by remember { mutableStateOf(true) }

    fun onAction(action: String) {
        when (action) {
            "C" -> {
                displayText = "0"
                operand1 = ""
                operator = ""
                isNewOp = true
            }
            "+", "-", "*", "/" -> {
                operand1 = displayText
                operator = action
                isNewOp = true
            }
            "=" -> {
                if (operator.isNotEmpty() && operand1.isNotEmpty()) {
                    val num1 = operand1.toDoubleOrNull() ?: 0.0
                    val num2 = displayText.toDoubleOrNull() ?: 0.0
                    var result = 0.0

                    when (operator) {
                        "+" -> result = num1 + num2
                        "-" -> result = num1 - num2
                        "*" -> result = num1 * num2
                        "/" -> result = if (num2 != 0.0) num1 / num2 else 0.0
                    }

                    displayText = if (result % 1.0 == 0.0) {
                        result.toInt().toString()
                    } else {
                        result.toString()
                    }

                    operator = ""
                    operand1 = ""
                    isNewOp = true
                }
            }
            else -> {
                if (isNewOp) {
                    displayText = action
                    isNewOp = false
                } else {
                    displayText += action
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = displayText,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp)
                .wrapContentHeight(Alignment.Bottom),
            textAlign = TextAlign.End,
            fontSize = 64.sp,
            fontWeight = FontWeight.Light,
            color = Color.Black
        )

        val buttons = listOf(
            listOf("1", "2", "3", "+"),
            listOf("4", "5", "6", "-"),
            listOf("7", "8", "9", "*"),
            listOf("C", "0", "=", "/")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { btn ->
                    Button(
                        onClick = { onAction(btn) },
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF7E57C2)
                        )
                    ) {
                        Text(
                            text = btn,
                            fontSize = 28.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
