package br.edu.utfpr.financeflow.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import br.edu.utfpr.financeflow.utils.MoneyFormatter

@Composable
fun MoneyText(
    value: Double,
    modifier: Modifier = Modifier,
    prefix: String = "",
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = prefix + MoneyFormatter.format(value),
        modifier = modifier,
        color = color,
        style = style
    )
}
