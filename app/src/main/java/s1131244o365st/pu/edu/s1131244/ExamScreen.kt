package s1131244o365st.pu.edu.s1131244

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExamScreen(viewModel: ExamViewModel) {

    val config = LocalConfiguration.current

    val screenWidthPx = config.screenWidthDp * config.densityDpi / 160
    val screenHeightPx = config.screenHeightDp * config.densityDpi / 160

    val halfHeightDp = (config.screenHeightDp / 2).dp   // 螢幕高度 1/2

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ) {

        // 🔵 左上角色：role.png
        Image(
            painter = painterResource(id = R.drawable.role0),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopStart)
                .offset(y = halfHeightDp - 300.dp), // 底部貼齊螢幕一半高度
            contentScale = ContentScale.Fit
        )

        // 🔵 右上角色：role1.png
        Image(
            painter = painterResource(id = R.drawable.role1),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopEnd)
                .offset(y = halfHeightDp - 300.dp),
            contentScale = ContentScale.Fit
        )

        // 🔵 左下角色：role2.png
        Image(
            painter = painterResource(id = R.drawable.role2),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomStart),
            contentScale = ContentScale.Fit
        )

        // 🔵 右下角色：role3.png
        Image(
            painter = painterResource(id = R.drawable.role3),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.BottomEnd),
            contentScale = ContentScale.Fit
        )

        // 🟡 中央主內容
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 圖片
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

            Text(text = viewModel.score, fontSize = 22.sp)
        }
    }
}
