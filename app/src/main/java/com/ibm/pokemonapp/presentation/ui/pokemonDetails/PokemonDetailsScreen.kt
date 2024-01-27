package com.ibm.pokemonapp.presentation.ui.pokemonDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.ibm.pokemonapp.R
import com.ibm.pokemonapp.data.source.network.response.PokemonResponse
import com.ibm.pokemonapp.domain.model.Resource
import com.ibm.pokemonapp.presentation.ui.theme.Blue
import com.ibm.pokemonapp.presentation.ui.theme.Red
import com.ibm.pokemonapp.presentation.ui.theme.Roboto
import com.ibm.pokemonapp.utils.Consts.DREAM_WORLD_IMAGES_URL
import com.ibm.pokemonapp.utils.InfoPair
import kotlinx.coroutines.flow.first

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    pokemonColor: Color,
    pokemonName: String?,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {

    val pokemonDetails by produceState(
        initialValue = Resource.Loading(),
        key1 = viewModel,
        key2 = pokemonName
    ) {
        value = pokemonName?.let { viewModel.getPokemonDetails(it.toLowerCase()).first() }!!
    }

    Column(
        modifier = Modifier
            .background(pokemonColor)
    )
    {
        TopBar(navController = navController)
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            PokemonDetailState(
                pokemonDetails = pokemonDetails,
                imageModifier = Modifier
                    .size(200.dp)
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                modifier = Modifier.fillMaxWidth(),
                loadingModifier = Modifier
                    .size(64.dp)
                    .padding(16.dp)
                    .align(Alignment.Center),
                textModifier = Modifier.align(Alignment.Center)
            )
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
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = pokemonDetails.types.getOrNull(0)?.type?.name ?: "N/A",
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
                .background(
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
        ) {
            Text(
                text = pokemonDetails.types.getOrNull(1)?.type?.name ?: "N/A",
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
    val imageUrl = "$DREAM_WORLD_IMAGES_URL${pokemonDetails.id}.svg"
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
            value = pokemonDetails.abilities.getOrNull(0)?.ability?.name ?: "N/A",
        ),
        InfoPair(
            iconRes = R.drawable.baseline_catching_pokemon_24,
            titleRes = R.string.skills,
            value = pokemonDetails.abilities.getOrNull(1)?.ability?.name ?: "N/A",
        ),
        modifier = modifier
    )
}

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

@Composable
fun GenderProgressBar(
    pokemonDetails: PokemonResponse
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = 0.6f,
            color = Blue,
            trackColor = Red,
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
                painter = painterResource(R.drawable.baseline_male_24),
                contentDescription = "Male Percent",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
            )
            Icon(
                painter = painterResource(R.drawable.baseline_female_24),
                contentDescription = "Female Percent",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun PokemonDetailState(
    pokemonDetails: Resource<PokemonResponse>,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
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
                    GenderProgressBar(pokemon)
                }
            }
        }

        is Resource.Error -> {
            Text(
                text = pokemonDetails.message!!,
                color = Red,
                modifier = textModifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}


