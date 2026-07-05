# Decisões de Projeto

Registro das principais decisões arquiteturais do FinanceFlow e suas
justificativas.

## Arquitetura MVVM

Optou-se por **MVVM** com três camadas bem definidas:

- **data** — modelos, banco (SQLiteOpenHelper) e repositório.
- **ui** — telas e componentes Compose, contendo apenas lógica de interface.
- **viewmodel** — `TransactionViewModel`, que concentra os estados e as ações.

A UI observa `StateFlow` expostos pela ViewModel e envia eventos por meio de
funções. A ViewModel nunca acessa o banco diretamente; todo o acesso passa pelo
`TransactionRepository`. Isso mantém responsabilidades separadas e torna a
ViewModel testável.

## Repository com interface

`TransactionRepository` é uma interface implementada por
`SqliteTransactionRepository`. A interface permite substituir o acesso a dados
por um `FakeTransactionRepository` nos testes, sem depender do framework Android
nem de bibliotecas externas.

## Persistência com SQLiteOpenHelper

O trabalho exige SQLiteOpenHelper. O esquema fica isolado em `FinanceContract`,
a criação/atualização em `FinanceDbHelper` e o mapeamento cursor → modelo em
`SqliteTransactionRepository`. Nenhuma instrução SQL aparece na UI.

## Modelo de dados

A data é armazenada como `dateMillis` (Long) em vez de texto. Isso permite
ordenar o extrato corretamente por data e formatar a exibição de forma
consistente. O tipo é gravado como texto (`INCOME`/`EXPENSE`) para legibilidade.
O `Transaction` expõe `signedAmount`, centralizando a regra de sinal.

## Fuso horário do DatePicker

O `DatePicker` do Material 3 retorna a data em UTC (meia-noite). Para evitar
que o dia exiba um valor deslocado em fusos negativos (como o do Brasil), o
`DateFormatter` formata em UTC, mantendo coerência entre a data escolhida e a
data exibida.

## Estado sem coroutines de I/O

O volume de dados é pequeno e local. As operações do repositório são síncronas,
mantendo o código simples e alinhado ao conteúdo da disciplina, sem introduzir
complexidade desnecessária de threads ou dispatchers.

## Sem bibliotecas de injeção de dependência

Não foram usados Hilt, Dagger, Koin ou Room. A `ViewModel` é criada por uma
`ViewModelProvider.Factory` simples, e o repositório é montado na
`MainActivity`. Isso respeita a restrição do trabalho de usar apenas o que foi
ensinado.

## Material 3, tema e Dynamic Color

O tema próprio define cores e tipografia e suporta modo claro/escuro. O
**Dynamic Color** é aplicado em Android 12+ com verificação de versão. As cores
semânticas de receita (verde) e despesa (vermelho) são fornecidas via
`CompositionLocal`, adaptando-se a cada tema.

## Navegação

`Navigation Compose` conecta a tela de extrato (início) à tela de lançamento.
Uma única instância de `TransactionViewModel` é compartilhada entre os destinos,
garantindo que o extrato reflita imediatamente um novo lançamento.

## Testes

Os testes cobrem as regras de negócio: parsing/formatação monetária, validação
de campos, cálculo de saldo e comportamento da ViewModel. Usam apenas JUnit e um
repositório falso, sem instrumentação Android.
