package br.edu.utfpr.financeflow.utils

data class TransactionFormErrors(
    val description: String? = null,
    val amount: String? = null,
    val date: String? = null
) {
    val isValid: Boolean
        get() = description == null && amount == null && date == null
}

object TransactionValidator {

    fun validate(
        description: String,
        amountText: String,
        dateMillis: Long?
    ): TransactionFormErrors {
        val parsedAmount = MoneyFormatter.parse(amountText)
        return TransactionFormErrors(
            description = if (description.isBlank()) "Informe a descrição" else null,
            amount = when {
                amountText.isBlank() -> "Informe o valor"
                parsedAmount == null -> "Valor inválido"
                parsedAmount <= 0.0 -> "O valor deve ser maior que zero"
                else -> null
            },
            date = if (dateMillis == null) "Selecione a data" else null
        )
    }
}
