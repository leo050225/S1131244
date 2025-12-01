package s1131244o365st.pu.edu.s1131244

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ExamScreen(viewModel: ExamViewModel) {

    val config = LocalConfiguration.current
    val screenWidthPx = config.screenWidthDp * config.densityDpi / 160
    val screenHeightPx = config.screenHeightDp * config.densityDpi / 160
    val halfHeightDp = (config.screenHeightDp / 2).dp

    val serviceImages = listOf(
        R.drawable.service0,
        R.drawable.service1,
        R.drawable.service2,
        R.drawable.service3
    )

    var serviceImg by remember { mutableStateOf(serviceImages.random()) }
    var posY by remember { mutableStateOf(0f) }
    var posX by remember { mutableStateOf((screenWidthPx / 2).toFloat()) }
    var collisionText by remember { mutableStateOf("") }

    val roleSizePx = 300.dp.value * config.densityDpi / 160
    val serviceSizePx = 150.dp.value * config.densityDpi / 160

    val roles = listOf(
        Pair(0f, halfHeightDp.value * config.densityDpi / 160 - roleSizePx),
        Pair(screenWidthPx - roleSizePx, halfHeightDp.value * config.densityDpi / 160 - roleSizePx),
        Pair(0f, screenHeightPx - roleSizePx),
        Pair(screenWidthPx - roleSizePx, screenHeightPx - roleSizePx)
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(100)
            posY += 20f

            var collided = false
            for ((index, rolePos) in roles.withIndex()) {
                val roleX = rolePos.first
                val roleY = rolePos.second
                if (posX + serviceSizePx > roleX &&
                    posX < roleX + roleSizePx &&
                    posY + serviceSizePx > roleY &&
                    posY < roleY + roleSizePx
                ) {
                    collisionText = when (index) {
                        0 -> "碰撞嬰幼兒"
                        1 -> "碰撞兒童"
                        2 -> "碰撞成人"
                        3 -> "碰撞一般民眾"
                        else -> ""
                    }
                    posY = 0f
                    posX = (screenWidthPx / 2).toFloat()
                    serviceImg = serviceImages.random()
                    collided = true
                    break
                }
            }

            if (!collided && posY >= screenHeightPx) {
                collisionText = "掉到最下方"
                posY = 0f
                posX = (screenWidthPx / 2).toFloat()
                serviceImg = serviceImages.random()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    posX += dragAmount.x
                    if (posX < 0) posX = 0f
                    if (posX > screenWidthPx - serviceSizePx) posX = screenWidthPx - serviceSizePx
                }
            }
    ) {

        Image(
            painter = painterResource(id = serviceImg),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .offset {
                    Offset(posX, posY).toIntOffset()
                },
            contentScale = ContentScale.Fit
        )

        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopStart)
                .offset(y = halfHeightDp - 300.dp),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopEnd)
                .offset(y = halfHeightDp - 300.dp),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Fit
        )
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.Fit
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.happy),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "瑪利亞基金會服務大考驗", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "作者：${viewModel.author}", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = viewModel.getScreenInfo(screenWidthPx, screenHeightPx),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = viewModel.score + " " + collisionText, fontSize = 22.sp)
        }
    }
}

fun Offset.toIntOffset() = androidx.compose.ui.unit.IntOffset(
    x = this.x.toInt(),
    y = this.y.toInt()
)
