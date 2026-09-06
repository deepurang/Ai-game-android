package com.deepurang.aiquizbattle;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.libraries.ads.mobile.sdk.MobileAds;
import com.google.android.libraries.ads.mobile.sdk.initialization.InitializationConfig;
import com.google.android.libraries.ads.mobile.sdk.banner.AdSize;
import com.google.android.libraries.ads.mobile.sdk.banner.AdView;
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAdRequest;
import com.google.android.libraries.ads.mobile.sdk.banner.AdLoadCallback;
import com.google.android.libraries.ads.mobile.sdk.banner.BannerAd;
import com.google.android.libraries.ads.mobile.sdk.common.LoadAdError;

public class MainActivity extends Activity {

    private WebView webView;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.loadUrl("https://deepurang.github.io/Ai-game/");

        root.addView(
            webView,
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1
            )
        );

        adView = new AdView(this);

        root.addView(
            adView,
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        );

        setContentView(root);

        new Thread(() -> {
            MobileAds.initialize(
                this,
                new InitializationConfig.Builder(
                    "ca-app-pub-3940256099942544~3347511713"
                ).build(),
                initializationStatus -> {
                    runOnUiThread(this::loadTestBanner);
                }
            );
        }).start();
    }

    private void loadTestBanner() {
        AdSize adSize =
            AdSize.getLargeAnchoredAdaptiveBannerAdSize(this, 360);

        BannerAdRequest request =
            new BannerAdRequest.Builder(
                "ca-app-pub-3940256099942544/9214589741",
                adSize
            ).build();

        adView.loadAd(
            request,
            new AdLoadCallback<BannerAd>() {
                @Override
                public void onAdLoaded(BannerAd ad) {
                    // Test banner loaded successfully.
                }

                @Override
                public void onAdFailedToLoad(LoadAdError error) {
                    // Test banner failed to load.
                }
            }
        );
    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }

        if (webView != null) {
            webView.destroy();
        }

        super.onDestroy();
    }
}
