package cn.jailedbird.study.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.jailedbird.study.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
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
    // Text(
    //     text = "Hello $name!",
    //     modifier = modifier
    // )
    // Box(
    //     modifier = Modifier
    //         .fillMaxWidth()
    //         .fillMaxHeight(), contentAlignment = Alignment.Center
    // ) {
    //     Column {
    //         Box(
    //             modifier = Modifier
    //                 .size(200.dp)
    //                 .background(Color.Red)
    //         )
    //         Spacer(modifier = Modifier.height(10.dp))
    //         Box(
    //             modifier = Modifier
    //                 .padding(25.dp)
    //                 .size(100.dp)
    //                 .background(Color.Red)
    //         ){
    //             Box(modifier = Modifier.fillMaxHeight().fillMaxHeight().background(Color.Blue))
    //         }
    //     }
    // }
    Text(
        text = "Ceshi",
        modifier = Modifier
            .padding(horizontal = 20.dp)

            .fillMaxWidth()
            .height(100.dp)
            .background(Color.Red)
            .padding(horizontal = 20.dp)
            .clickable {
                Log.d("TAG", "Click")
            }
            .background(Color.Blue)

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

