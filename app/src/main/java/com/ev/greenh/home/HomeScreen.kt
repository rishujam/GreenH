package com.ev.greenh.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ev.greenh.R
import com.ev.greenh.common.commonui.ActivityViewModel
import com.ev.greenh.common.commonui.LogoFontFamily
import com.ev.greenh.common.commonui.Mat3OnBg
import com.ev.greenh.common.commonui.Mat3OnPrimary
import com.ev.greenh.common.commonui.Mat3OnSurfaceVariant
import com.ev.greenh.common.commonui.Mat3Primary
import com.ev.greenh.common.commonui.Mat3Secondary
import com.ev.greenh.common.commonui.Mat3Surface
import com.ev.greenh.common.commonui.Mat3SurfaceVariant
import com.ev.greenh.common.commonui.NunitoFontFamily
import com.ev.greenh.common.commonui.composable.AlertPrompt
import com.ev.greenh.common.commonui.composable.LoadingAnimation
import com.ev.greenh.common.commonui.event.ActivityEvent
import com.ev.greenh.common.commonui.model.DialogModel
import com.ev.greenh.grow.ui.LocalPlantListFragment
import com.ev.greenh.plantidentify.ui.PlantIdentifyFragment
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.Constants
import com.ev.greenh.util.findActivity
import com.example.testing.Tags

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun HomeScreen(
    activityViewModel: ActivityViewModel?
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = activityViewModel?.state?.showDialog != null,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            activityViewModel?.state?.showDialog?.let {
                AlertPrompt(
                    model = it,
                    cancelText = "Go Back",
                    onCancel = {
                        activityViewModel.dismissDialog()
                    }
                )
            }
        }
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
                .fillMaxHeight(0.5f)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        if (activityViewModel?.isFeatureEnabled(Constants.Feature.IDENTIFY) == true) {
                            val activity = context.findActivity()
                            val frag = PlantIdentifyFragment()
                            (activity as? MainActivity)?.setCurrentFragmentBack(frag)
                        } else {
                            activityViewModel?.onEvent(
                                ActivityEvent.ShowDialog(
                                    DialogModel(
                                        "Test Dialog",
                                        "This feature will be soon available"
                                    )
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                val placeholder = R.drawable.gradient_sun
                val imageRequest = ImageRequest.Builder(context)
                    .data("https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/plant_scan.jpeg?alt=media&token=db2395a2-2ec7-4030-9513-4f09c45285a6")
                    .memoryCacheKey("https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/plant_scan.jpeg?alt=media&token=db2395a2-2ec7-4030-9513-4f09c45285a6")
                    .diskCacheKey("https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/plant_scan.jpeg?alt=media&token=db2395a2-2ec7-4030-9513-4f09c45285a6")
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fallback(placeholder)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
                val painter = rememberAsyncImagePainter(
                    imageRequest,
                )
                val state = painter.state
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Mat3Primary, Mat3Secondary),
                                ),
                                alpha = 0.7f
                            )
                            .padding(vertical = 12.dp),
                    ) {
                        Text(
                            text = "Free Plant Identifier",
                            color = Mat3OnPrimary,
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if (state is AsyncImagePainter.State.Loading) {
                    LoadingAnimation()
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
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Mat3Surface)
                    .clickable {
                        if (activityViewModel?.isFeatureEnabled(Constants.Feature.GROW) == true) {
                            val activity = context.findActivity()
                            val fragment = LocalPlantListFragment()
                            (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
                        } else {
                            activityViewModel?.onEvent(
                                ActivityEvent.ShowDialog(
                                    DialogModel(
                                        "Test Dialog",
                                        "This feature will be soon available"
                                    )
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                val placeholder = R.drawable.gradient_sun
                val imageRequest = ImageRequest.Builder(context)
                    .data("https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/learn_plants.jpeg?alt=media&token=c0db965f-1f94-43a4-9afc-912c12f1c8f6")
                    .memoryCacheKey("https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/learn_plants.jpeg?alt=media&token=c0db965f-1f94-43a4-9afc-912c12f1c8f6")
                    .diskCacheKey("https://firebasestorage.googleapis.com/v0/b/gardenershub-ece08.appspot.com/o/learn_plants.jpeg?alt=media&token=c0db965f-1f94-43a4-9afc-912c12f1c8f6")
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fallback(placeholder)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .build()
                val painter = rememberAsyncImagePainter(
                    imageRequest,
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Mat3Secondary, Mat3Primary),
                                ),
                                alpha = 0.7f
                            )
                            .padding(vertical = 12.dp),
                    ) {
                        Text(
                            text = "Learn To Grow",
                            color = Mat3OnPrimary,
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                if (state is AsyncImagePainter.State.Loading) {
                    LoadingAnimation()
                }
            }
        }
    }

}