package br.edu.utfpr.financeflow.navigation

sealed class Screen(val route: String) {
    data object Statement : Screen("statement")
    data object Launch : Screen("launch")
}
