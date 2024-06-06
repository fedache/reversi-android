package com.afedare.reversi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afedare.reversi.board.BoardUi
import com.afedare.reversi.home.Home
import com.afedare.reversi.service.BoardViewModel
import com.afedare.reversi.ui.theme.ReversiTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReversiTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(stringResource(R.string.reversi))
                            }
                        )
                    },
                    content = { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            AppNavigator()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { Home(navController) }
        composable("single") {
            val boardViewModel: BoardViewModel = viewModel(
                factory = BoardViewModel.provideFactory()
            )
            val boardState by boardViewModel.boardState.collectAsState()
            Column {
                Text(
                    stringResource(R.string.player, stringResource(boardState.currentPlayerLabel)),
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp))
                Text(
                    stringResource(R.string.black_count, boardState.blackCount),
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp))
                Text(
                    stringResource(R.string.white_count, boardState.whiteCount),
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 4.dp))
                if (stringResource(boardState.winner) != "") {
                    Text(
                        stringResource(boardState.winner),
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 4.dp))
                }
                BoardUi(boardState.colors, boardState.availablePlays, boardState.currentPlayer) { i, j ->
                    boardViewModel.play(i, j)
                }
            }

        }
    }
}