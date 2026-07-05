package br.edu.utfpr.financeflow.data.model

data class Transaction(
    val id: Long = 0L,
    val description: String,
    val amount: Double,
    val dateMillis: Long,
    val type: TransactionType
) {
    val signedAmount: Double
        get() = if (type == TransactionType.INCOME) amount else -amount
}
