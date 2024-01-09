package idv.natsucamellia.yuumi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import idv.natsucamellia.yuumi.ui.YuumiApp
import idv.natsucamellia.yuumi.ui.theme.YuumiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            YuumiTheme {
                YuumiApp()
            }
        }
    }
}