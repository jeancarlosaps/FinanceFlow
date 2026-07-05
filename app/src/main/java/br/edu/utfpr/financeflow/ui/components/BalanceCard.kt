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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.financeflow.R
import br.edu.utfpr.financeflow.ui.theme.FinanceFlowTheme
import br.edu.utfpr.financeflow.ui.theme.LocalFinanceColors
import br.edu.utfpr.financeflow.utils.DateFormatter

@Composable
fun BalanceCard(
    balance: Double,
    income: Double,
    expense: Double,
    modifier: Modifier = Modifier,
    lastUpdatedMillis: Long? = null
) {
    val financeColors = LocalFinanceColors.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("balance_card"),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = stringResource(R.string.balance_current_label),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            MoneyText(
                value = balance,
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                BalanceSummaryItem(
                    label = stringResource(R.string.balance_income_label),
                    value = income,
                    valueColor = financeColors.income,
                    modifier = Modifier.weight(1f)
                )
                BalanceSummaryItem(
                    label = stringResource(R.string.balance_expense_label),
                    value = expense,
                    valueColor = financeColors.expense,
                    modifier = Modifier.weight(1f)
                )
            }
            if (lastUpdatedMillis != null) {
                Text(
                    text = stringResource(
                        R.string.balance_last_updated,
                        DateFormatter.dateTime(lastUpdatedMillis)
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(top = 16.dp)
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
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        MoneyText(
            value = value,
            modifier = Modifier.padding(top = 2.dp),
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
            modifier = Modifier.padding(16.dp),
            lastUpdatedMillis = 0L
        )
    }
}
