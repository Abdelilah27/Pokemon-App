package com.ibm.pokemonapp.presentation.ui.pokemonList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ibm.pokemonapp.R
import com.ibm.pokemonapp.data.models.PokemonListEntry
import com.ibm.pokemonapp.presentation.ui.theme.Red
import com.ibm.pokemonapp.presentation.ui.theme.Roboto
import com.ibm.pokemonapp.presentation.ui.theme.RobotoCondensed
import com.ibm.pokemonapp.utils.UIText


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
                        append(stringResource(R.string.welcome_title))
                    }
                    append("\n")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(R.string.welcome_title_))
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
                hint = stringResource(R.string.search_hint),
            )

            Spacer(modifier = Modifier.height(16.dp))

            PokemonList(navController)

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


@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.pokemonList } // TODO
    val isEndReached by remember { viewModel.isEndReached }
    val isLoading by remember { viewModel.isLoading }
    val workflowError by remember { viewModel.workflowError }


    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        // TODO
        val itemCount = if (pokemonList.size % 2 == 0) {
            pokemonList.size / 2
        } else {
            pokemonList.size / 2 + 1 // 14.2
        }
        items(itemCount) { currentIndex ->
            if (currentIndex >= itemCount - 1 && !isEndReached && !isLoading) { // Check if we're at the bottom
                viewModel.getPokemonList()
            }
            PokemonRow(
                index = currentIndex,
                pokemonListEntry = pokemonList,
                navController = navController
            )
        }
    }
    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
        }
        if (workflowError.message.isNotEmpty()) {
            Retry(error = workflowError.message) {
                viewModel.getPokemonList()
            }
        }
    }
}


@Composable
fun PokemonListEntry(
    entry: PokemonListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultPokemonColor = MaterialTheme.colorScheme.tertiary
    var pokemonColor by remember {
        mutableStateOf(defaultPokemonColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(1f) // TODO
            .background(pokemonColor)
            .clickable {
                navController.navigate(
                    "pokemon_details_screen/${pokemonColor.toArgb()}/${entry.pokemonName}"
                )
            }
    ) {
        Column {
            val imageUrl = entry.imageUrl
            val placeholderImage = R.drawable.ic_pokemon_logo
            val imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .memoryCacheKey(imageUrl)
                .diskCacheKey(imageUrl)
                .error(placeholderImage)
                .decoderFactory(SvgDecoder.Factory())
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .build()
            SubcomposeAsyncImage(
                model = imageRequest,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                contentDescription = entry.pokemonName,
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                onSuccess = { success ->
                    val drawable = success.result.drawable
                    viewModel.generatePokemonColor(drawable) { color ->
                        pokemonColor = color
                    }
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = entry.pokemonName,
                    fontFamily = RobotoCondensed,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Forward",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

// TODO
@Composable
fun PokemonRow(
    index: Int,
    pokemonListEntry: List<PokemonListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokemonListEntry(
                entry = pokemonListEntry[index * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            val currentRow = index * 2
            if (pokemonListEntry.size >= currentRow + 2) {
                PokemonListEntry(
                    entry = pokemonListEntry[currentRow + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.heightIn(16.dp))
    }
}


@Composable
fun Retry(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier
                .align(CenterHorizontally)
                .background(Red, RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Red)
        ) {
            Text(
                text = stringResource(R.string.retry),
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

