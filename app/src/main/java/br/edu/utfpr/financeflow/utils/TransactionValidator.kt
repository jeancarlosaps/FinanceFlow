package br.edu.utfpr.financeflow.utils

enum class ValidationError {
    DESCRIPTION_REQUIRED,
    AMOUNT_REQUIRED,
    AMOUNT_INVALID,
    AMOUNT_NOT_POSITIVE,
    DATE_REQUIRED
}

data class TransactionFormErrors(
    val description: ValidationError? = null,
    val amount: ValidationError? = null,
    val date: ValidationError? = null
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
            description = if (description.isBlank()) ValidationError.DESCRIPTION_REQUIRED else null,
            amount = when {
                amountText.isBlank() -> ValidationError.AMOUNT_REQUIRED
                parsedAmount == null -> ValidationError.AMOUNT_INVALID
                parsedAmount <= 0.0 -> ValidationError.AMOUNT_NOT_POSITIVE
                else -> null
            },
            date = if (dateMillis == null) ValidationError.DATE_REQUIRED else null
        )
    }
}
