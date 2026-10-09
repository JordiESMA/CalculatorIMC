package com.estiradojordi.newCalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.estiradojordi.newCalculator.ui.theme.NewCalculatorTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewCalculatorTheme {
                BmiCalculatorApp()
            }
        }
    }
}

@Composable
fun BmiCalculatorApp() {
    var nom by remember { mutableStateOf("") }
    var pes by remember { mutableIntStateOf(80) }
    var alcada by remember { mutableIntStateOf(180) }
    var resultado by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Título
            Text(
                text = "BMI Calculator",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Campo de texto para Nombre ("Nom")
            TextField(
                value = nom,
                onValueChange = { nom = it },
                placeholder = { Text("Nom") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Peso ("Pes")
            Text(
                text = "Pes",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$pes Kg",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Button(
                    onClick = { if (pes > 20) pes-- },
                    shape = CircleShape,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("-", fontSize = 20.sp)
                }
                Button(
                    onClick = { if (pes < 300) pes++ },
                    shape = CircleShape
                ) {
                    Text("+", fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Altura ("Alcada")
            Text(
                text = "Alcada: $alcada cm",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = alcada.toFloat(),
                onValueChange = { alcada = it.toInt() },
                valueRange = 100f..220f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Calcular BMI
            Button(
                onClick = {
                    val alcadaMetres = alcada / 100f
                    if (alcadaMetres > 0) {
                        val bmi = pes / (alcadaMetres * alcadaMetres)
                        val nomTexto = if (nom.isNotBlank()) "Hola $nom, " else ""
                        val categoria = when {
                            bmi < 18.5f -> "Bajo peso"
                            bmi < 25.0f -> "Peso normal"
                            bmi < 30.0f -> "Sobrepeso"
                            else -> "Obesidad"
                        }
                        resultado = "${nomTexto}tu BMI es ${String.format(Locale.US, "%.1f", bmi)} ($categoria)"
                    }
                },
                shape = CircleShape
            ) {
                Text("Calcular BMI", fontSize = 16.sp)
            }

            // Mostrar el resultado si existe
            if (resultado.isNotEmpty()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = resultado,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BmiCalculatorAppPreview() {
    NewCalculatorTheme {
        BmiCalculatorApp()
    }
}
