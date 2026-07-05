package br.edu.utfpr.financeflow.ui.statement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.edu.utfpr.financeflow.ui.components.BalanceCard
import br.edu.utfpr.financeflow.ui.components.EmptyState
import br.edu.utfpr.financeflow.ui.components.TransactionItem
import br.edu.utfpr.financeflow.viewmodel.StatementUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatementScreen(
    state: StatementUiState,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("FinanceFlow") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Filled.Add, contentDescription = "Novo lançamento")
            }
        }
    ) { innerPadding ->
        if (state.transactions.isEmpty()) {
            EmptyState(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    BalanceCard(
                        balance = state.balance,
                        income = state.totalIncome,
                        expense = state.totalExpense
                    )
                }
                item {
                    Text(
                        text = "Extrato",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(
                    items = state.transactions,
                    key = { it.id }
                ) { transaction ->
                    TransactionItem(transaction = transaction)
                }
            }
        }
    }
}
