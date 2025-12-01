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

    // 對應每個服務圖示的正確角色索引 (0~3) 及答案文字
    val serviceAnswers = mapOf(
        R.drawable.service0 to Pair(0, "極早期療育，屬於嬰幼兒方面的服務"),
        R.drawable.service1 to Pair(1, "離島服務，屬於兒童方面的服務"),
        R.drawable.service2 to Pair(2, "極重多障，屬於成人方面的服務"),
        R.drawable.service3 to Pair(3, "輔具服務，屬於一般民眾方面的服務")
    )

    var serviceImg by remember { mutableStateOf(serviceImages.random()) }
    var posY by remember { mutableStateOf(0f) }
    var posX by remember { mutableStateOf((screenWidthPx / 2).toFloat()) }
    var collisionText by remember { mutableStateOf("") }
    var answerText by remember { mutableStateOf("") }
    var showAnswer by remember { mutableStateOf(false) }
    var collidedRoleName by remember { mutableStateOf("") } // 碰撞到的角色名稱

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
                    collided = true

                    // 記錄碰撞角色名稱
                    collidedRoleName = when (index) {
                        0 -> "嬰幼兒"
                        1 -> "兒童"
                        2 -> "成人"
                        3 -> "一般民眾"
                        else -> ""
                    }

                    val (correctRoleIndex, answer) = serviceAnswers[serviceImg] ?: Pair(-1, "")
                    val currentScore = viewModel.score.toIntOrNull() ?: 0
                    if (index == correctRoleIndex) {
                        viewModel.score = (currentScore + 1).toString()
                        collisionText = "正確！"
                    } else {
                        viewModel.score = maxOf(currentScore - 1, 0).toString()
                        collisionText = "錯誤！"
                    }

                    answerText = answer
                    showAnswer = true

                    // 暫停 3 秒再出下一題
                    delay(3000)
                    showAnswer = false
                    posY = 0f
                    posX = (screenWidthPx / 2).toFloat()
                    serviceImg = serviceImages.random()
                    collisionText = ""
                    answerText = ""
                    collidedRoleName = ""
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

        // 服務圖示
        Image(
            painter = painterResource(id = serviceImg),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .offset { Offset(posX, posY).toIntOffset() },
            contentScale = ContentScale.Fit
        )

        // 角色圖示
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

        // 中央資訊欄
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

            // 分數顯示：碰撞到角色名稱
            val scoreDisplayText = if (collidedRoleName.isNotEmpty()) {
                "成績: ${viewModel.score}分（$collidedRoleName）"
            } else {
                "成績: ${viewModel.score}分"
            }
            Text(text = scoreDisplayText, fontSize = 22.sp)
        }

        // 下方答案提示欄
        if (showAnswer) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(Color.White.copy(alpha = 0.9f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = answerText,
                    fontSize = 20.sp,
                    color = if (collisionText == "正確！") Color.Green else Color.Red
                )
            }
        }
    }
}

fun Offset.toIntOffset() = androidx.compose.ui.unit.IntOffset(
    x = this.x.toInt(),
    y = this.y.toInt()
)
