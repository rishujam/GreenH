package com.ev.greenh.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.ev.greenh.R
import com.ev.greenh.commonui.LogoFontFamily
import com.ev.greenh.commonui.Mat3OnBg
import com.ev.greenh.commonui.Mat3OnPrimary
import com.ev.greenh.commonui.Mat3OnSurface
import com.ev.greenh.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.commonui.Mat3Primary
import com.ev.greenh.commonui.Mat3Surface
import com.ev.greenh.commonui.Mat3SurfaceVariant
import com.ev.greenh.commonui.NunitoFontFamily
import com.ev.greenh.commonui.composable.LoadingAnimation
import com.ev.greenh.grow.ui.GrowFragment
import com.ev.greenh.grow.ui.LocalPlantStep1Fragment
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.ui.plants.PlantFragment
import com.ev.greenh.util.findActivity
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            Text(
                text = "GH",
                modifier = Modifier.padding(start = 16.dp, top = 4.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Mat3Primary,
                    fontFamily = LogoFontFamily
                )
            )
            Text(
                text = "Gardeners Hub",
                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Mat3OnBg,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Mat3SurfaceVariant)
            ) {
                Column {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_idea_bulb),
                            contentDescription = Tags.TIP_ICON,
                            modifier = Modifier
                                .size(36.dp)
                                .padding(start = 12.dp, top = 12.dp)
                        )
                        Text(
                            text = "Tip of the day",
                            modifier = Modifier.padding(start = 8.dp, top = 12.dp),
                            fontSize = 16.sp,
                            fontFamily = NunitoFontFamily,
                            color = Mat3OnSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "Grow your plants do not grow your plants. It is to grow your plants helps",
                        fontSize = 16.sp,
                        fontFamily = NunitoFontFamily,
                        color = Mat3OnSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 4.dp, end = 12.dp, bottom = 12.dp)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        val activity = context.findActivity()
                        val fragment = LocalPlantStep1Fragment()
                        (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                    },
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(
                    "https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/what_to_grow.jpeg?alt=media&token=0584f716-c390-4cc7-ba97-f2893e1b8468",
                )
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    LoadingAnimation()
                }
                val transition by animateFloatAsState(
                    targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
                    label = Tags.TRANSIT_SHOP_BANNER
                )
                Image(
                    painter = painter,
                    contentDescription = Tags.SHOP_CARD_BANNER,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(.8f + (.2f * transition))
                        .graphicsLayer { rotationX = (1f - transition) * 5f }
                        .alpha(Math.min(1f, transition / .2f)),
                    contentScale = ContentScale.Crop,
                )
                this@Column.AnimatedVisibility(
                    visible = state is AsyncImagePainter.State.Success,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        text = "Find what to grow",
                        color = Mat3OnPrimary,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.width(150.dp)
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        val activity = context.findActivity()
                        val fragment = GrowFragment()
                        (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                    },
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(
                    "https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/how_to_grow_banner1.jpeg?alt=media&token=65c33908-44da-4ffa-bd66-e994d9f5b705",
                )
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    LoadingAnimation()
                }
                val transition by animateFloatAsState(
                    targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
                    label = Tags.TRANSIT_SHOP_BANNER
                )
                Image(
                    painter = painter,
                    contentDescription = Tags.SHOP_CARD_BANNER,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(.8f + (.2f * transition))
                        .graphicsLayer { rotationX = (1f - transition) * 5f }
                        .alpha(Math.min(1f, transition / .2f)),
                    contentScale = ContentScale.Crop,
                )
                this@Column.AnimatedVisibility(
                    visible = state is AsyncImagePainter.State.Success,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        text = "How to grow",
                        color = Mat3OnPrimary,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Mat3Surface)
                    .clickable {
                        val activity = context.findActivity()
                        val fragment = PlantFragment()
                        (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                    },
                contentAlignment = Alignment.Center
            ) {
                val painter = rememberAsyncImagePainter(
                    "https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/shop_plant_banner.jpeg?alt=media&token=df920887-c6c3-4763-bd8a-71388bdf95b9",
                )
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading) {
                    LoadingAnimation()
                }
                val transition by animateFloatAsState(
                    targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
                    label = Tags.TRANSIT_SHOP_BANNER
                )
                Image(
                    painter = painter,
                    contentDescription = Tags.SHOP_CARD_BANNER,
                    modifier = Modifier
                        .fillMaxSize()
                        .scale(.8f + (.2f * transition))
                        .graphicsLayer { rotationX = (1f - transition) * 5f }
                        .alpha(Math.min(1f, transition / .2f)),
                    contentScale = ContentScale.Crop,
                )
                this@Column.AnimatedVisibility(
                    visible = state is AsyncImagePainter.State.Success,
                    enter = slideInVertically() + fadeIn(),
                    exit = slideOutVertically() + fadeOut()
                ) {
                    Text(
                        text = "Buy Plants",
                        color = Mat3OnPrimary,
                        fontFamily = NunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            }
        }
    }

}