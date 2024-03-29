package com.ibm.pokemonapp.presentation.ui.pokemonDetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ibm.pokemonapp.BuildConfig
import com.ibm.pokemonapp.R
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.presentation.ui.theme.Red
import com.ibm.pokemonapp.presentation.ui.theme.Roboto
import com.ibm.pokemonapp.utils.InfoPair
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    pokemonColor: Color,
    pokemonName: String?,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    // Increment the refreshState to trigger a UI update when the user clicks the "Retry" button
    var refreshState by remember { mutableStateOf(0) }

    // The `pokemonDetails` state is updated using produceState, with dependencies on viewModel, pokemonName, and refreshState
    val pokemonDetails by produceState(
        initialValue = Resource.Loading(),
        key1 = viewModel,
        key2 = pokemonName,
        key3 = refreshState
    ) {
        value =
            pokemonName?.let { viewModel.getPokemonDetails(it.lowercase(Locale.ROOT)).first() }!!
    }
    Column(
        modifier = Modifier
            .background(pokemonColor)
            .fillMaxHeight()
            .verticalScroll(state = rememberScrollState())
    )
    {
        // TopBar Section
        TopBar(navController = navController)

        // To Display the Pokemon details based on the current state
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            pokemonName?.let { name ->
                PokemonDetailState(
                    pokemonName = name,
                    viewModel = viewModel,
                    pokemonDetails = pokemonDetails,
                    modifier = Modifier.fillMaxWidth(),
                    imageModifier = Modifier
                        .size(200.dp)
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    loadingModifier = Modifier
                        .size(64.dp)
                        .padding(16.dp)
                        .align(Alignment.Center),
                    retryModifier = Modifier.align(Alignment.Center),
                    onRetry = {
                        // Increment the refreshState to trigger a UI update
                        refreshState++
                    },
                )
            }
        }
    }
}


@Composable
fun TopBar(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .clickable {
                    navController.popBackStack()
                }
        )
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "Like Icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    // TODO
                }
        )
    }
}

@Composable
fun PokemonNameAndNumber(pokemonName: String?, pokemonNumber: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = pokemonName ?: "N/A",
            fontFamily = Roboto,
            fontWeight = FontWeight.Black,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(
            text = "No: $pokemonNumber",
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun PokemonTypesRow(pokemonDetails: PokemonResponse) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )

        ) {
            Text(
                text = pokemonDetails.types?.getOrNull(0)?.type?.name ?: "N/A",
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = pokemonDetails.types?.getOrNull(1)?.type?.name ?: "N/A",
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun PokemonImage(pokemonDetails: PokemonResponse, modifier: Modifier) {
    val imageUrl = BuildConfig.DREAM_WORLD_IMAGES_URL + "/${pokemonDetails.id}.svg"
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
        modifier = modifier,
        contentDescription = "Pokemon Image",
        loading = {
            // Loading indicator while the image is being fetched
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.scale(0.5f)
            )
        },
        onSuccess = {}
    )
}

@Composable
fun Divider() {
    Spacer(
        modifier = Modifier
            .padding(start = 48.dp, end = 48.dp, bottom = 16.dp)
            .height(1.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(24.dp))
    )
}

// Display the Pokemon skills data including weight, height, category, and skills
@Composable
private fun PokemonSkillsData(
    modifier: Modifier,
    pokemonDetails: PokemonResponse
) {
    PokemonInfoRow(
        InfoPair(
            iconRes = R.drawable.baseline_spoke_24,
            titleRes = R.string.weight,
            value = "${pokemonDetails.weight} Kg"
        ),
        InfoPair(
            iconRes = R.drawable.baseline_height_24,
            titleRes = R.string.height,
            value = "${pokemonDetails.height} M"
        ),
        modifier = modifier
    )

    PokemonInfoRow(
        InfoPair(
            iconRes = R.drawable.baseline_category_24,
            titleRes = R.string.category,
            value = pokemonDetails.abilities?.getOrNull(0)?.ability?.name ?: "N/A",
        ),
        InfoPair(
            iconRes = R.drawable.baseline_catching_pokemon_24,
            titleRes = R.string.skills,
            value = pokemonDetails.abilities?.getOrNull(1)?.ability?.name ?: "N/A",
        ),
        modifier = modifier
    )
}

// Row to display two sets of information side by side
@Composable
fun PokemonInfoRow(infoPair: InfoPair, infoPair_: InfoPair, modifier: Modifier) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        PokemonInfoPair(infoPair, modifier)
        Spacer(modifier = Modifier.width(12.dp))
        PokemonInfoPair(infoPair_, modifier)
    }
}

@Composable
fun PokemonInfoPair(infoPair: InfoPair, modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Row {
            Icon(
                painter = painterResource(infoPair.iconRes),
                contentDescription = infoPair.value,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(infoPair.titleRes),
                fontFamily = Roboto,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .shadow(5.dp, RoundedCornerShape(8.dp))
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )

                .fillMaxWidth()
        ) {
            Text(
                text = infoPair.value,
                fontFamily = Roboto,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

// Display an animated progress bar with the specified global state percentage
@Composable
fun StatesProgressBar(
    globalState: Float
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue =
        if (animationPlayed) globalState else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = currentPercentage.value,
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_percent_24),
                contentDescription = "Global State",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PokemonDetailState(
    pokemonName: String,
    viewModel: PokemonDetailsViewModel,
    pokemonDetails: Resource<PokemonResponse>,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    retryModifier: Modifier,
    onRetry: () -> Unit,
) {
    when (pokemonDetails) {
        is Resource.Success -> {
            val pokemonData = pokemonDetails.data
            Column {
                pokemonData?.let { pokemon ->
                    PokemonNameAndNumber(
                        pokemonName = pokemonDetails.data.name,
                        pokemon.id
                    )
                    PokemonTypesRow(pokemon)
                    PokemonImage(
                        pokemonDetails.data,
                        imageModifier.align(Alignment.CenterHorizontally)
                    )
                    Divider()
                    PokemonSkillsData(modifier = modifier.weight(1f), pokemonDetails = pokemon)
                    StatesProgressBar(viewModel.calculateGlobalState(pokemon.stats))
                }
            }
        }

        is Resource.Error -> {
            // Display a Retry composable with the error message and retry callback
            val coroutineScope = rememberCoroutineScope()
            Retry(error = pokemonDetails.message.toString(), onRetry = onRetry, retryModifier) {
                coroutineScope.launch {
                    viewModel.getPokemonDetails(pokemonName.lowercase(Locale.ROOT))
                }
            }
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
fun Retry(
    error: String,
    onRetry: () -> Unit,
    retryModifier: Modifier,
    function: () -> Job
) {
    Column(modifier = retryModifier) {
        Text(error, color = Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
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


