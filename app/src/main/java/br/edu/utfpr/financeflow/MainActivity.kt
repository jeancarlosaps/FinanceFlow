package br.edu.utfpr.financeflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.utfpr.financeflow.data.database.FinanceDbHelper
import br.edu.utfpr.financeflow.data.repository.SqliteTransactionRepository
import br.edu.utfpr.financeflow.navigation.FinanceNavGraph
import br.edu.utfpr.financeflow.ui.theme.FinanceFlowTheme
import br.edu.utfpr.financeflow.viewmodel.TransactionViewModel
import br.edu.utfpr.financeflow.viewmodel.TransactionViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = SqliteTransactionRepository(FinanceDbHelper(applicationContext))

        setContent {
            FinanceFlowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: TransactionViewModel = viewModel(
                        factory = TransactionViewModelFactory(repository)
                    )
                    FinanceNavGraph(viewModel = viewModel)
                }
            }
        }
    }
}
