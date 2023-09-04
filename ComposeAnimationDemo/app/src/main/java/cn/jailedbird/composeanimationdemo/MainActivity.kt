package cn.jailedbird.composeanimationdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.jailedbird.composeanimationdemo.ui.theme.ComposeAnimationDemoTheme

private val s1 = "MainActivityMainActivityMainActivityMainActivityMainActivityMainActivity"
private val s2 = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var v by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
            .background(Color.Blue)
            .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { v = !v }) {
            Text(text = "显示/隐藏")
        }
        Spacer(modifier = Modifier.height(20.dp))
        /*if (v) {*/
        // AnimatedVisibility(visible = v) {
        //     Text(
        //         text = "Hello $name!",
        //         modifier = modifier
        //     )
        // }
        /*}*/

        Text(
            text = if (v) s1 else s2, modifier = Modifier
                .wrapContentHeight()
                .padding(20.dp)
        )

    }


}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeAnimationDemoTheme {
        Greeting("Android")
    }
}