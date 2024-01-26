package com.ibm.pokemonapp.presentation.ui.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ibm.pokemonapp.R
import com.ibm.pokemonapp.presentation.ui.theme.Roboto


@Composable
fun PokemonListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxSize() // TODO
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_pokemon_logo),
                contentDescription = "Pokemon logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("What Are You")
                    }
                    append("\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Looking For?")
                    }
                },
                fontFamily = Roboto,
                fontWeight = FontWeight.Black,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Start)
            )

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                hint = "Search ...",
            )

        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    // TODO
    var query by remember {
        mutableStateOf("")
    }

    var isHintVisible by remember {
        mutableStateOf(hint.isNotEmpty() && query.isEmpty())
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = modifier) {
        BasicTextField(
            value = query,
            onValueChange = {
                query = it
                isHintVisible = it.isEmpty()
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.secondary), // TODO
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(8.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp) // TODO
                .onFocusChanged {
                    if (it.isFocused) {
                        keyboardController?.show() // TODO
                    }
                },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(query)
                    keyboardController?.hide()
                }
            )
        )

        if (isHintVisible) {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        )
    }
}

