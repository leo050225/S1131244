package s1131244o365st.pu.edu.s1131244

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
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

    // 讀取螢幕 px
    val config = LocalConfiguration.current
    val screenWidthPx = config.screenWidthDp * config.densityDpi / 160
    val screenHeightPx = config.screenHeightDp * config.densityDpi / 160

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 圖片
        Image(
            painter = painterResource(id = R.drawable.happy),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 🔥 新增的標題行
        Text(
            text = "瑪利亞基金會服務大考驗",
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 作者資訊
        Text(text = "作者：${viewModel.author}", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(10.dp))

        // 螢幕尺寸
        Text(
            text = viewModel.getScreenInfo(screenWidthPx, screenHeightPx),
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // 成績
        Text(text = viewModel.score, fontSize = 22.sp)
    }
}
