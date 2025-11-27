package com.example.fabriscan;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import androidx.appcompat.app.AppCompatActivity;

public class NewChatbotActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chatbot);

        // Set status bar color to blue (#5ACEFF) to match the header
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(android.graphics.Color.parseColor("#5ACEFF"));
            // Set status bar icons to white for better visibility on blue background
            getWindow().getDecorView().setSystemUiVisibility(0);
        }

        // Initialize views
        webView = findViewById(R.id.chatbotWebView);

        // Setup WebView
        setupWebView();
        loadChatbot();
    }

    private void setupWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void loadChatbot() {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>FabriScan ChatBot</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #ffffff;\n" +
                "        }\n" +
                "        .chat-container {\n" +
                "            height: 100vh;\n" +
                "            background: white;\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "        }\n" +
                "        iframe {\n" +
                "            width: 100%;\n" +
                "            height: 100%;\n" +
                "            border: none;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"chat-container\">\n" +
                "        <iframe\n" +
                "            src=\"https://www.chatbase.co/chatbot-iframe/I00cE3dLhO5xIq9TF6pcd\"\n" +
                "            width=\"100%\"\n" +
                "            style=\"height: 100%; min-height: 700px\"\n" +
                "            frameborder=\"0\"\n" +
                "        ></iframe>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";

        webView.loadDataWithBaseURL("https://www.chatbase.co", htmlContent, "text/html", "UTF-8", null);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
