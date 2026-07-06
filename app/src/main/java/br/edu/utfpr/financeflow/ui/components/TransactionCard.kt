package br.edu.utfpr.financeflow.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.edu.utfpr.financeflow.R
import br.edu.utfpr.financeflow.data.model.Transaction
import br.edu.utfpr.financeflow.data.model.TransactionType
import br.edu.utfpr.financeflow.ui.theme.FinanceFlowTheme
import br.edu.utfpr.financeflow.ui.theme.LocalFinanceColors
import br.edu.utfpr.financeflow.utils.DateFormatter

@Composable
fun TransactionCard(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val financeColors = LocalFinanceColors.current
    val isIncome = transaction.type == TransactionType.INCOME
    val accentColor = if (isIncome) financeColors.income else financeColors.expense
    val icon = if (isIncome) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown
    val typeDescription = stringResource(if (isIncome) R.string.cd_income else R.string.cd_expense)
    val prefix = if (isIncome) "+ " else "- "

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .testTag("transaction_card")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = typeDescription,
                    tint = accentColor
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = transaction.description,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = DateFormatter.format(transaction.dateMillis),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            MoneyText(
                value = transaction.amount,
                prefix = prefix,
                color = accentColor,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionCardPreview() {
    FinanceFlowTheme(dynamicColor = false) {
        TransactionCard(
            transaction = Transaction(
                id = 1L,
                description = "Salário",
                amount = 2500.0,
                dateMillis = 0L,
                type = TransactionType.INCOME
            )
        )
    }
}
