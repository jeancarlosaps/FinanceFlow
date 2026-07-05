# Checklist da Avaliação

Mapeamento dos itens avaliados na disciplina para a implementação do FinanceFlow.

## Itens obrigatórios

- [x] **Desenvolvimento da tela principal** — `StatementScreen` exibe saldo e extrato.
- [x] **Desenvolvimento da tela de listagem** — extrato em `LazyColumn` com todos os lançamentos.
- [x] **Consistência dos campos de entrada (lançamento)** — `TransactionValidator` valida valor, descrição e data com mensagens amigáveis; valor zero é rejeitado.
- [x] **Persistência de dados no banco** — `SQLiteOpenHelper` via `FinanceDbHelper` e `SqliteTransactionRepository`.
- [x] **Navegabilidade entre telas** — Navigation Compose entre `StatementScreen` e `LaunchScreen`.
- [x] **Organização do código (MVC ou MVVM)** — MVVM com `TransactionViewModel`, camadas `data`/`ui`/`viewmodel`.
- [x] **Apresentação dos dados com adapter** — `LazyColumn` + `TransactionItem` (equivalente Compose ao adapter).

## Itens bônus (plus)

- [x] **Uso de DatePicker** — `DatePicker` do Material 3 na tela de lançamento.
- [x] **Diferenciação de crédito e débito** — receitas em verde, despesas em vermelho, com ícones distintos.
- [x] **Apresentação do saldo** — `BalanceCard` com saldo, total de receitas e total de despesas.

## Requisitos técnicos do trabalho

- [x] Kotlin
- [x] Jetpack Compose
- [x] Material Design 3
- [x] MVVM
- [x] SQLiteOpenHelper
- [x] Navigation
- [x] LazyColumn
- [x] DatePicker
- [x] Dynamic Color (Android 12+)
- [x] Tema claro
- [x] Tema escuro

## Qualidade

- [x] Build sem erros
- [x] Sem warnings relevantes
- [x] Testes unitários (22 testes, todos passando)
- [x] Código organizado em camadas, UI sem acesso direto ao SQLite
- [x] README, PROJECT_DECISIONS e LICENSE
