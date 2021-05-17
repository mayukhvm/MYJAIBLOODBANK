package com.myjai.bloodbank;
///made by mayukhvm
import android.Manifest;
import android.annotation.SuppressLint;
//import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
//import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Environment;
//import android.os.Parcelable;
//import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
//import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
//import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

//import java.io.File;


public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {
    SwipeRefreshLayout swipe;
    String Url;
    WebView web;
    ProgressBar progressBar;
   // private FirebaseAuth mAuth;
    private ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;
    private final static int FILECHOOSER_RESULTCODE = 1;
    //int close = 0;

    private RelativeLayout relLayout;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relLayout = findViewById(R.id.relLayout);
        Thread th = new Thread()
        {
            @Override
            public void run() {
                try
                {
                    sleep(4000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    UpdateHelper.with(MainActivity.this)
                            .onUpdateCheck(MainActivity.this)
                            .check();
                }
            }
        };
        th.start();


        web= findViewById(R.id.webView);
        progressBar= findViewById(R.id.progressBar);
        WebSettings webSettings = web.getSettings();

        progressBar.setVisibility(View.VISIBLE);
        //isNetworkAvailable();
        Url = "https://www.jaiexportinvestmentco.in/myjai-bloodbank/";
        web.loadUrl("https://www.jaiexportinvestmentco.in/myjai-bloodbank/");
        webSettings.setJavaScriptEnabled(true);

        swipe= findViewById(R.id.swipe);

        swipe.setOnRefreshListener(() -> {
            progressBar.setVisibility(View.VISIBLE);
            swipe.setRefreshing(false);
            swipe.setEnabled(false);
            WebAction();
        });

        /*if (!isNetworkAvailable()) {
            // write your toast message("Please check your internet connection")
            Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            Snackbar.make(relLayout,"No internet connection",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebAction();
                        }
                    })
                    .setActionTextColor(Color.BLUE)
                    .setActionTextColor(Color.YELLOW)
                    .show();
            //String url = web.getUrl();
            //if (url.startsWith("tel:") || url.startsWith("mailto:"))
            //{
            //  web.loadUrl("https://www.skillgenics.com/rosh/contact.php");
            //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // startActivity(intent);

            // }else{
            web.loadUrl("file:///android_asset/sample.html");
            // }
            progressBar.setVisibility(View.INVISIBLE);
            //swipe.setRefreshing(false);
        } else {
            // code
            WebAction();
            //swipe.setRefreshing(true);

        }*/
        if (!isNetworkAvailable()) {
            // write your toast message("Please check your internet connection")
            //Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            Snackbar.make(relLayout,"No internet connection",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", v -> WebAction())
                    .setActionTextColor(Color.BLUE)
                    .setActionTextColor(Color.YELLOW)
                    .show();
            swipe.setEnabled(true);
            //String url = web.getUrl();
            //if (url.startsWith("tel:") || url.startsWith("mailto:"))
            //{
            //  web.loadUrl("https://www.skillgenics.com/rosh/contact.php");
            //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // startActivity(intent);

            // }else{
            web.loadUrl("file:///android_asset/sample.html");
            // }
            progressBar.setVisibility(View.INVISIBLE);
            //swipe.setRefreshing(false);
        } else {
            // code
            WebAction();
            //Snackbar.make(relLayout,"Internet Connected",Snackbar.LENGTH_SHORT)
            //.show();
            swipe.setEnabled(true);


            //swipe.setRefreshing(true);

        }

    }
    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
//        else { //permission is automatically granted on sdk<23 upon installation
//        }
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onUpdateCheckListener(final String urlApp) {
        //create Alert Dialog
//        AlertDialog alertDialog=new AlertDialog.Builder(this)
//                .setTitle("New Version Available")
//                .setMessage("Please update to new version to continue use")
//                .setPositiveButton("UPDATE",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface,int i){
//                        Toast.makeText(MainActivity.this,""+urlApp,Toast.LENGTH_SHORT).show();
//                    }
//                }).setNegativeButton("CANCEL",new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i){
//                        dialogInterface.dismiss();
//                    }
//
//                }).create();
//        alertDialog.show();

    }

    public class WebAppInterface{
        Context mContext;
        WebAppInterface(Context c){
            mContext=c;
        }


        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(mContext,toast,Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void WebAction() {
        web = (WebView) findViewById(R.id.webView);
        if (!isNetworkAvailable()) {
            // write your toast message("Please check your internet connection")
            //Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            Snackbar.make(relLayout,"No internet connection",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebAction();
                        }
                    })
                    .setActionTextColor(Color.BLUE)
                    .setActionTextColor(Color.YELLOW)
                    .show();
            swipe.setEnabled(true);
            //String url = web.getUrl();
            //if (url.startsWith("tel:") || url.startsWith("mailto:"))
            //{
            //  web.loadUrl("https://www.skillgenics.com/rosh/contact.php");
            //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // startActivity(intent);

            // }else{
            web.loadUrl("file:///android_asset/sample.html");
            // }
            progressBar.setVisibility(View.INVISIBLE);
            //swipe.setRefreshing(false);
        } else {
            // code

            web.loadUrl(Url);
            Snackbar.make(relLayout,"Loading...",Snackbar.LENGTH_SHORT)
                    .show();
            swipe.setEnabled(true);


            //swipe.setRefreshing(true);

        }



        //web.getSettings().setUserAgentString("Mozilla/5.0 (Android 4.4; Mobile; rv:41.0) Gecko/41.0 Firefox/41.0");
        //web  = new WebView(this);
        web.getSettings().setJavaScriptEnabled(true); // enable javascript


        //web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setAppCacheEnabled(true);
        //web.setWebViewClient(new Callback());
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setAllowContentAccess(true);

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                progressBar.setProgress(newProgress);

                super.onProgressChanged(view, newProgress);
            }
            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected void openFileChooser(ValueCallback uploadMsg, String acceptType)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }

                uploadMessage = filePathCallback;

                Intent intent = fileChooserParams.createIntent();
                try
                {
                    startActivityForResult(intent, REQUEST_SELECT_FILE);
                } catch (ActivityNotFoundException e)
                {
                    uploadMessage = null;
                    Toast.makeText(MainActivity.this, "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }


        });

        web.setWebViewClient(new WebViewClient() {



            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //swipe.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);
                if (failingUrl.startsWith("tel:") || failingUrl.startsWith("whatsapp:"))
                {
                    String url1 = "https://www.jaiexportinvestmentco.in/myjai-bloodbank/";
                    web.loadUrl(url1);

                }else if(failingUrl.contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/details.php?id=")){
                    nonet(failingUrl);
                    //web.loadUrl("file:///android_asset/sample.html");
                }else if(failingUrl.contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/search.php")){
                    //nonet(failingUrl);
                    if (isNetworkAvailable()){
                        web.loadUrl("https://www.jaiexportinvestmentco.in/myjai-bloodbank/");
                    }else{
                        nonet(failingUrl);
                    }
                    //web.loadUrl("file:///android_asset/sample.html");
                }else{
                   // web.loadUrl("file:///android_asset/sample.html");
                    nonet(failingUrl);
                }



                //swipe.setRefreshing(false);
            }
            public boolean  shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("https://wa.me")) {
                    web.stopLoading();
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url.replace("+",""))));

                    return true;

                }else if(url.contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/details.php?id=")){
                    nonet(url);
                    //web.loadUrl("file:///android_asset/sample.html");
                    return true;
                }else if(url.contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/search.php")){

                    if (isNetworkAvailable()){
                        web.loadUrl("https://www.jaiexportinvestmentco.in/myjai-bloodbank/");
                    }else{
                        nonet(url);
                    }
                        //web.loadUrl("file:///android_asset/sample.html");
                    return true;
                } else {
                    nonet(url);
                    return false;
                }
            }


            public void onPageFinished(WebView view, String url) {
                //do your stuff here

                swipe.setEnabled(false);
                progressBar.setVisibility(View.INVISIBLE);
                if(isNetworkAvailable() && !Url.equals("file:///android_asset/sample.html")){
                    Url = web.getUrl();
                }else {
                    Url="https://www.jaiexportinvestmentco.in/myjai-bloodbank/";
                }

                if(!isNetworkAvailable()){
                    //WebAction();
                    nonet(url);
                    web.stopLoading();
                }

            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                if (url.startsWith("tel:") && !url.startsWith("https://wa.me"))
                {
                    //String url1 = "https://www.skillgenics.com/rosh/contact.php";
                    //web.loadUrl(url1);
                    web.stopLoading();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);

                }
                if(!isNetworkAvailable()){
                    //WebAction();
                    nonet(url);
                    web.stopLoading();
                }

               // swipe.setRefreshing(true);

            }
        });





    }
    public void nonet(String url){
        if (!isNetworkAvailable()) {
            // write your toast message("Please check your internet connection")
            //Toast.makeText(this,"Please check your internet connection",Toast.LENGTH_SHORT).show();
            Snackbar.make(relLayout,"No internet connection",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebAction();
                        }
                    })
                    .setActionTextColor(Color.BLUE)
                    .setActionTextColor(Color.YELLOW)
                    .show();
            //String url = web.getUrl();
            //if (url.startsWith("tel:") || url.startsWith("mailto:"))
            //{
            //  web.loadUrl("https://www.skillgenics.com/rosh/contact.php");
            //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // startActivity(intent);

            // }else{
            swipe.setEnabled(true);
            web.loadUrl("file:///android_asset/sample.html");
            // }
            progressBar.setVisibility(View.INVISIBLE);
            //swipe.setRefreshing(false);
        }else{
            web.loadUrl(url);
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == REQUEST_SELECT_FILE)
            {
                if (uploadMessage == null)
                    return;
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                uploadMessage = null;
            }
        }
        else if (requestCode == FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
        else
            Toast.makeText(MainActivity.this, "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }


    /*private class Callback extends WebViewClient{  //HERE IS THE MAIN CHANGE.

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:") || url.startsWith("mailto:"))
            {
                web.loadUrl("https://www.skillgenics.com/rosh/contact.php");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);

            }else{
                web.loadUrl("file:///android_asset/sample.html");
            }
            return (false);
        }

    }*/

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event){
        if((KeyCode==KeyEvent.KEYCODE_BACK)&& web.canGoBack() && isNetworkAvailable()){

            if(web.getUrl().contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/login.php")){
                web.loadUrl("https://www.jaiexportinvestmentco.in/myjai-bloodbank/");
            }else if(web.getUrl().contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/details.php")){
                web.loadUrl("https://www.jaiexportinvestmentco.in/myjai-bloodbank/");
            }else if(web.getUrl().contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/") || web.getUrl().contains("https://www.jaiexportinvestmentco.in/myjai-bloodbank/index.php")){


                    MainActivity.this.finish();



            }else{
                web.goBack();
            }

            return true;
        }
        return super.onKeyDown(KeyCode,event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //WebAction();
        //web.loadUrl("https://www.skillgenics.com/rosh/rosh.php");

    }

    @Override
    protected void onStart() {
        super.onStart();
       // FirebaseUser currentUser = mAuth.getCurrentUser();
        isStoragePermissionGranted();
        //updateUI(currentUser);
    }
}

