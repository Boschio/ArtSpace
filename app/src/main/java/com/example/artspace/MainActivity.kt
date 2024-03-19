package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artspace.data.DataSource
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      ArtSpaceTheme {
        NavHost(
          navController = navController, startDestination = Screen.Home.route + "/{id}"
        ) {
          composable(
            Screen.Home.route + "/{id}", arguments = listOf(navArgument("id") {
              type = NavType.IntType
              defaultValue = 0
            })
          ) {
            HomePage(navController)
          }
          composable(
            Screen.Artist.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
          ) {
            ArtistPage(navController)
          }
        }
      }
    }
  }
}

@Composable
fun ArtistPage(navController: NavController) {
  val id = navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
  val art = DataSource.arts[id]

  // ARTIST PAGE section A
  // TODO: 1. Artist Profile including image, name, and info (birthplace, and years alive)
  Column(modifier = Modifier
    .verticalScroll(rememberScrollState())
    .padding(20.dp)) {
    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_medium)))
    Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
      Image(painter = painterResource(id = art.artistImageId),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .clip(CircleShape)
          .size(140.dp)
          .border(
            BorderStroke(3.dp, Color.LightGray),
            CircleShape
          )
          .padding(3.dp)
          .clip(CircleShape))
      Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_medium)))
      Column(modifier = Modifier) {
        Text(
          text = stringResource(id = art.artistId),
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          textAlign = TextAlign.Center
        )
        Text(
          text = stringResource(id = art.artistInfoId),
          fontSize = 14.sp,
          textAlign = TextAlign.Center
        )
      }
    }

    Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))

    // ARTIST PAGE section B
    // TODO: 2  Artist bio

    Text(
      text = stringResource(id = art.artistBioId),
      textAlign = TextAlign.Left
    )

    // DO NOT MODIFY THE FOLLOWING CODE
    // You can use the following code to navigate to the previous screen:
    // ARTIST PAGE section C
    // TODO: 3 place the code below in the proper Row or Column layout
    // entire code block is encapsulated in a Column above
    Button(onClick = {
      navController.navigate(Screen.Home.route + "/$id")
    }) {
      Text(text = stringResource(id = R.string.back))
    }
  }
}


@Composable
fun ArtWall(
  artistId: Int,
  artImageId: Int,
  artDescriptionId: Int,
  navController: NavController,
) {

  // HOME PAGE section A

  // TODO: 4. Add image of artwork
  Column(modifier = Modifier.fillMaxWidth()) {
      Image(
        painter = painterResource(id = artImageId),
        contentDescription = stringResource(id = artDescriptionId),
        modifier = Modifier
          .padding(15.dp)
          .border(10.dp, Color.LightGray)
          .align(Alignment.CenterHorizontally)
          // TODO: 5. Add a click listener to navigate to the artist page
          .clickable {
            navController.navigate(Screen.Artist.route + "/$artistId")
          },
        contentScale = ContentScale.Fit
      )
  }
}

@Composable
fun ArtDescriptor(artTitleId: Int, artistId: Int, artYearId: Int) {

  // HOME PAGE section B
  Column(
    modifier = Modifier
      .padding(vertical = 10.dp)
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
      // TODO: 6. Add title of artwork
      Text(
        text = stringResource(id = artTitleId),
        modifier = Modifier.padding(bottom = 12.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
      )
      // TODO: 7. Add artist name and year of artwork
      Text(
        text = stringResource(id = artistId) + "  " + stringResource(id = artYearId),
        textAlign = TextAlign.Center
      )
  }
}


@Composable
fun DisplayController(current: Int, move: (Int) -> Unit) {

  // HOME PAGE section C
  var prevEnabled by remember { mutableStateOf(false) }
  var nextEnabled by remember { mutableStateOf(true) }

  if (current in 1..3) {
    prevEnabled = true
    nextEnabled = true
  }
  if (current <= 0) {
    prevEnabled = false
    nextEnabled = true
  }
  if (current >= 4) {
    prevEnabled = true
    nextEnabled = false
  }

  Row(modifier = Modifier
    .padding(vertical = 10.dp)
    .fillMaxWidth()
    .wrapContentWidth(Alignment.CenterHorizontally)) {
    // TODO: 9. Add a button to navigate to the previous artwork
    FilledTonalButton(modifier = Modifier
      .padding(horizontal = 20.dp)
      .width(110.dp),
      enabled = prevEnabled,
      onClick = {
        if(current > 0) {
          move(current - 1)
        }
      }) {
      Text("Previous")
    }
    // TODO: 10. Add a button to navigate to the next artwork
    Button(modifier = Modifier
      .padding(horizontal = 20.dp)
      .width(110.dp),
      enabled = nextEnabled,
      onClick = {
        if(current < 4) {
          move(current + 1)
        }
      }) {
      Text("Next")
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
  var current by remember {
    mutableIntStateOf(
      navController.currentBackStackEntry?.arguments?.getInt(
        "id"
      ) ?: 0
    )
  }
  val art = DataSource.arts[current]

  Scaffold(topBar = {
    CenterAlignedTopAppBar(
      title = { Text(text = stringResource(id = R.string.app_name)) },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary
      )
    )
  }) { innerPadding ->
    /**
     *The children without weight (a) are measured first. After that, the remaining space in the column
     * is spread among the children with weights (b), proportional to their weight. If you have 2
     * children with weight 1f, each will take half the remaining space.
     */
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(innerPadding)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f) // children with weight (b)
      ) {

        Spacer(modifier = Modifier.size(dimensionResource(id = R.dimen.spacer_extra_large)))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
        ) {
          ArtWall(current, art.artworkImageId, art.descriptionId, navController)
        }
      }
      // (a) children without weight
      ArtDescriptor(art.titleId, art.artistId, art.yearId)
      DisplayController(current) {
        current = if (it !in 0..<DataSource.arts.size) 0 else it
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
  ArtSpaceTheme {
    HomePage(rememberNavController())
  }
}
