package s1131244o365st.pu.edu.s1131244

import androidx.lifecycle.ViewModel

class ExamViewModel : ViewModel() {

    // 請改成你的系級與姓名
    val author = "資管二A 施聿觀"

    // 讀取螢幕解析度
    fun getScreenInfo(width: Int, height: Int): String {
        return "螢幕大小：$width x $height"
    }

    // 成績
    val score = "成績：0分"
}
