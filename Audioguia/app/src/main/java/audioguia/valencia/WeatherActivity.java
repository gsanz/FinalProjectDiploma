package audioguia.valencia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.ProgressBar;
import android.view.View;
import android.graphics.Bitmap;
import android.app.ProgressDialog;

public class WeatherActivity extends ActionBarActivity {
    private WebView navegador;
    private ProgressBar barraProgreso;
    private ProgressDialog dialogo;

    @Override
    @SuppressLint("JavascriptInterface")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        navegador = (WebView)findViewById(R.id.webkit_weather);
        navegador.getSettings().setJavaScriptEnabled(true);
        navegador.getSettings().setBuiltInZoomControls(false);
        Bundle data = getIntent().getExtras();
        navegador.loadUrl("file:///android_asset/kitesurf_html/temperature.html?ciudad=Valencia");
        navegador.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
           @Override
             public void onPageStarted(WebView view,String url,Bitmap favicon)
              {
                  dialogo = new ProgressDialog(WeatherActivity.this);
                  dialogo.setMessage("Cargando ..");
                  dialogo.setCancelable(true);
                  dialogo.show();
              }
           @Override
           public void onPageFinished(WebView view, String url){
             dialogo.dismiss();
           }
           }
        );

        barraProgreso = (ProgressBar) findViewById(R.id.barraProgreso);
        navegador.setWebChromeClient(new WebChromeClient(){
            @Override
        public void onProgressChanged(WebView view, int progreso){
                barraProgreso.setProgress(0);
                barraProgreso.setVisibility(View.VISIBLE);
                WeatherActivity.this.setProgress(progreso*1000);
                barraProgreso.incrementProgressBy(progreso);
                if (progreso == 100)
                {
                    barraProgreso.setVisibility(View.GONE);
                }
            }

        });
    }
}
