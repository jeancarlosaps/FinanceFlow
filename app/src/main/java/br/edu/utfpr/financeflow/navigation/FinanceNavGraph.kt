package br.edu.utfpr.financeflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.edu.utfpr.financeflow.ui.launch.LaunchScreen
import br.edu.utfpr.financeflow.ui.statement.StatementScreen
import br.edu.utfpr.financeflow.viewmodel.TransactionViewModel

@Composable
fun FinanceNavGraph(viewModel: TransactionViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Statement.route
    ) {
        composable(Screen.Statement.route) {
            val state by viewModel.statementState.collectAsStateWithLifecycle()
            StatementScreen(
                state = state,
                onAddClick = { navController.navigate(Screen.Launch.route) }
            )
        }
        composable(Screen.Launch.route) {
            val formState by viewModel.formState.collectAsStateWithLifecycle()
            LaunchScreen(
                state = formState,
                onDescriptionChange = viewModel::onDescriptionChange,
                onAmountChange = viewModel::onAmountChange,
                onDateSelected = viewModel::onDateSelected,
                onTypeChange = viewModel::onTypeChange,
                onSave = viewModel::save,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
