package com.ibm.pokemonapp.presentation.ui.pokemonDetails

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ibm.pokemonapp.R
import com.ibm.pokemonapp.presentation.ui.theme.Blue
import com.ibm.pokemonapp.presentation.ui.theme.Red
import com.ibm.pokemonapp.presentation.ui.theme.Roboto
import com.ibm.pokemonapp.utils.InfoPair

@Composable
fun PokemonDetailsScreen(
    navController: NavController,
    pokemonColor: Color,
    pokemonName: String?,
) {
    Surface(
        color = pokemonColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TopBar(navController = navController)
            PokemonNameAndNumber(pokemonName = pokemonName)
            PokemonTypesRow()
            PokemonImage()
            Divider()

            PokemonInfoRow(
                InfoPair(
                    iconRes = R.drawable.baseline_spoke_24,
                    titleRes = R.string.weight,
                    value = "90,5 Kg"
                ),
                InfoPair(
                    iconRes = R.drawable.baseline_height_24,
                    titleRes = R.string.height,
                    value = "1,8 m"
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            PokemonInfoRow(
                InfoPair(
                    iconRes = R.drawable.baseline_category_24,
                    titleRes = R.string.category,
                    value = "Blame"
                ),
                InfoPair(
                    iconRes = R.drawable.baseline_catching_pokemon_24,
                    titleRes = R.string.skills,
                    value = "Flame"
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            GenderProgressBar(genderPercentage = 0.8f)

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
fun PokemonNameAndNumber(pokemonName: String?) {
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
            text = "001" ?: "N/A",
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
fun PokemonTypesRow() {
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
                text = "Grass" ?: "N/A",
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
                text = "Poison" ?: "N/A",
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
fun PokemonImage() {
    Image(
        painter = painterResource(id = R.drawable.pokmon_one),
        contentDescription = "Pokemon logo",
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
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
fun GenderProgressBar(genderPercentage: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LinearProgressIndicator(
            progress = genderPercentage,
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




