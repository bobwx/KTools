package com.jiangkang.ktools;

import android.app.Application;
import android.content.Intent;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.webkit.WebView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.stetho.Stetho;
import com.jiangkang.tools.King;
import com.jiangkang.tools.service.ScanMusicService;
import com.jiangkang.weex.ImageAdapter;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jiangkang
 * @date 2017/9/6
 */
public class KApplication extends Application implements ReactApplication {


    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        enableStrictMode();

        if (BuildConfig.DEBUG) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Stetho.initializeWithDefaults(KApplication.this);
                        }
                    }
            ).start();

        }


        initLeakCanary();

        King.init(this);

        initARouter();

        initWeex();

    }

    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
    }

    private void initWeex() {

        InitConfig config = new InitConfig.Builder()
                .setImgAdapter(new ImageAdapter())
                .build();

        WXSDKEngine.initialize(this, config);

    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }

    private void enableStrictMode() {
        if (BuildConfig.DEBUG) {
            enableThreadPolicy();
            enableVmPolicy();
        }
    }

    private void enableVmPolicy() {
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build()
        );
    }

    private void enableThreadPolicy() {
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder()
                        .detectAll()
                        .penaltyLog()
                        .build()
        );
    }


    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
