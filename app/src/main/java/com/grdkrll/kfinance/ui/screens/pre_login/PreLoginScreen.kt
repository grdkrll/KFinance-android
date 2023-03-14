package com.grdkrll.kfinance.ui.screens.pre_login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.grdkrll.kfinance.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreLoginScreen(
    viewModel: PreLoginViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCarousel()
        ContinueButton(viewModel)
    }
}

fun getPageSymbol(pageNumber: Int, currentPage: Int) = if (currentPage == pageNumber) "●" else "○"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCarousel() {
    val currentPage = remember { mutableStateOf(0) }
    Column {
        HorizontalPager(
            pageCount = 2,
            modifier = Modifier.fillMaxHeight(0.8f)
        ) { page ->
            if (currentPage.value != page) {
                currentPage.value = page
            }
            if (page == 0) {
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.svg_prelogin_page0),
                            contentDescription = "page 0",

                            )
                        Text(
                            text = stringResource(id = R.string.pre_login_page_0_label),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp)
                        )
                    }
                }
            } else if (page == 1) {
                Box {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.svg_prelogin_page1),
                            contentDescription = "page 1"
                        )
                        Text(
                            text = stringResource(id = R.string.pre_login_page_1_label),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 32.dp)
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = getPageSymbol(0, currentPage.value),
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = getPageSymbol(1, currentPage.value),
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}

@Composable
fun ContinueButton(
    viewModel: PreLoginViewModel
) {
    Button(
        onClick = viewModel::redirectToLogin,
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(8.dp)
    ) {
        Text("Continue")
    }
}