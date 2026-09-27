package com.example.mywebviewapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp

// The URL that loads automatically when the app opens.
private const val TARGET_URL = "https://suman0405.pythonanywhere.com"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface {
                    WebViewScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen() {
    // Holds a reference to the WebView instance so the top bar's
    // refresh button can trigger a reload.
    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResourceCompat()) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
                actions = {
                    IconButton(onClick = { webViewRef?.reload() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh page"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        WebViewContainer(
            modifier = Modifier.padding(innerPadding),
            onWebViewCreated = { webView -> webViewRef = webView }
        )
    }
}

// Small helper so we don't need an extra import block change if you prefer
// stringResource(R.string.top_bar_title) directly — kept simple here.
@Composable
private fun stringResourceCompat(): String {
    return androidx.compose.ui.res.stringResource(id = R.string.top_bar_title)
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewContainer(
    modifier: Modifier = Modifier,
    onWebViewCreated: (WebView) -> Unit
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                // Enable JavaScript so the website behaves correctly.
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                // Keep all navigation (including link clicks) inside this
                // WebView instead of launching an external browser.
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        // Returning false tells the WebView to load the URL
                        // itself rather than handing it off to another app.
                        request?.url?.let { view?.loadUrl(it.toString()) }
                        return true
                    }
                }

                loadUrl(TARGET_URL)
                onWebViewCreated(this)
            }
        },
        update = { /* No dynamic updates needed; state is held via the WebView reference. */ }
    )
}
