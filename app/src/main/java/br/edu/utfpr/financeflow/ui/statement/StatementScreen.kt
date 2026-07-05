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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.financeflow.R
import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.ui.components.BalanceCard
import br.edu.utfpr.financeflow.ui.components.EmptyState
import br.edu.utfpr.financeflow.ui.components.TransactionCard
import br.edu.utfpr.financeflow.ui.theme.FinanceFlowTheme
import br.edu.utfpr.financeflow.viewmodel.StatementUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatementScreen(
    state: StatementUiState,
    onAddClick: () -> Unit,
    savedEvent: Boolean = false,
    onSavedEventConsumed: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val savedMessage = stringResource(R.string.snackbar_saved)

    LaunchedEffect(savedEvent) {
        if (savedEvent) {
            snackbarHostState.showSnackbar(savedMessage)
            onSavedEventConsumed()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.statement_title)) })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddClick,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text(stringResource(R.string.fab_new_entry)) },
                modifier = Modifier.testTag("fab_new_entry")
            )
        }
    ) { innerPadding ->
        if (state.transactions.isEmpty()) {
            EmptyState(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("transaction_list"),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    BalanceCard(
                        balance = state.balance,
                        income = state.totalIncome,
                        expense = state.totalExpense,
                        lastUpdatedMillis = state.lastUpdatedMillis
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.statement_section),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                items(
                    items = state.transactions,
                    key = { it.id }
                ) { transaction ->
                    TransactionCard(transaction = transaction)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatementScreenPreview() {
    FinanceFlowTheme(dynamicColor = false) {
        StatementScreen(
            state = StatementUiState(
                transactions = listOf(
                    Transaction(1L, "Salário", 2500.0, 0L, TransactionType.INCOME),
                    Transaction(2L, "Aluguel", 800.0, 0L, TransactionType.EXPENSE)
                ),
                balance = 1700.0,
                totalIncome = 2500.0,
                totalExpense = 800.0,
                lastUpdatedMillis = 0L
            ),
            onAddClick = {}
        )
    }
}
