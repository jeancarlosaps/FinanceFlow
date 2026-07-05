package br.edu.utfpr.financeflow.ui.launch

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.ui.components.TypeSelector
import br.edu.utfpr.financeflow.ui.theme.FinanceFlowTheme
import br.edu.utfpr.financeflow.utils.DateFormatter
import br.edu.utfpr.financeflow.viewmodel.LaunchFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchScreen(
    state: LaunchFormState,
    onDescriptionChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onDateSelected: (Long) -> Unit,
    onTypeChange: (TransactionType) -> Unit,
    onSave: () -> Boolean,
    onBack: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo lançamento") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = state.amount,
                onValueChange = onAmountChange,
                label = { Text("Valor (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = state.errors.amount != null,
                supportingText = { state.errors.amount?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = onDescriptionChange,
                label = { Text("Descrição") },
                isError = state.errors.description != null,
                supportingText = { state.errors.description?.let { Text(it) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            Box {
                OutlinedTextField(
                    value = state.dateMillis?.let { DateFormatter.format(it) } ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data") },
                    trailingIcon = {
                        Icon(Icons.Filled.CalendarMonth, contentDescription = null)
                    },
                    isError = state.errors.date != null,
                    supportingText = { state.errors.date?.let { Text(it) } },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }
            Spacer(Modifier.height(16.dp))

            Text("Tipo", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(4.dp))
            TypeSelector(selected = state.type, onSelect = onTypeChange)
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { if (onSave()) onBack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.dateMillis
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let(onDateSelected)
                    showDatePicker = false
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchScreenPreview() {
    FinanceFlowTheme(dynamicColor = false) {
        LaunchScreen(
            state = LaunchFormState(),
            onDescriptionChange = {},
            onAmountChange = {},
            onDateSelected = {},
            onTypeChange = {},
            onSave = { true },
            onBack = {}
        )
    }
}
