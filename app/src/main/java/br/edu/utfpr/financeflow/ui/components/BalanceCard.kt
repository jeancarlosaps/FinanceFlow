package br.edu.utfpr.financeflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.financeflow.ui.theme.FinanceFlowTheme
import br.edu.utfpr.financeflow.ui.theme.LocalFinanceColors
import br.edu.utfpr.financeflow.utils.MoneyFormatter

@Composable
fun BalanceCard(
    balance: Double,
    income: Double,
    expense: Double,
    modifier: Modifier = Modifier
) {
    val financeColors = LocalFinanceColors.current
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Saldo atual",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = MoneyFormatter.format(balance),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BalanceSummaryItem(
                    label = "Receitas",
                    value = income,
                    valueColor = financeColors.income,
                    modifier = Modifier.weight(1f)
                )
                BalanceSummaryItem(
                    label = "Despesas",
                    value = expense,
                    valueColor = financeColors.expense,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BalanceSummaryItem(
    label: String,
    value: Double,
    valueColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = MoneyFormatter.format(value),
            style = MaterialTheme.typography.titleMedium,
            color = valueColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BalanceCardPreview() {
    FinanceFlowTheme(dynamicColor = false) {
        BalanceCard(
            balance = 1700.0,
            income = 2500.0,
            expense = 800.0,
            modifier = Modifier.padding(16.dp)
        )
    }
}
