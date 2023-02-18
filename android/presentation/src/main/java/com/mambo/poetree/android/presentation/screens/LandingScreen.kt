package com.mambo.poetree.android.presentation.screens

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination

@Destination(start = true)
@Composable
fun LandingScreen() {
}

//@Destination(start = true)
//@Composable
//fun LandingScreen(
//    navController: DestinationsNavigator
//) {
//
//    Column(
//        modifier = Modifier.padding(24.dp),
//    ) {
//
//        Text(
//            modifier = Modifier.padding(top = 24.dp),
//            text = PoetreeApp().name(),
//            style = MaterialTheme.typography.h3,
//            fontWeight = FontWeight.Bold,
//        )
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f),
//        ) {
//            Text(
//                modifier = Modifier.padding(top = 16.dp),
//                text = PoetreeApp().dummyPoem(),
//                style = MaterialTheme.typography.body1,
//                fontWeight = FontWeight.Medium,
//            )
//            Text(
//                modifier = Modifier.padding(top = 16.dp),
//                text = PoetreeApp().dummyPoet(),
//                style = MaterialTheme.typography.body2,
//                fontWeight = FontWeight.SemiBold,
//            )
//        }
//
//        Button(
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {
//                navigateToAuth(navController)
//            }) {
//            Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
//        }
//
//    }
//}
//
//@Preview
//@Composable
//fun LandingScreenPreview() {
//
//    LandingScreen(navController = EmptyDestinationsNavigator)
//
//}
//
//private fun navigateToAuth(navController: DestinationsNavigator) {
//    navController.navigate(AuthScreenDestination)
//}