package com.lakala.cloudpos.avoslibrary;

import android.app.Activity;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Avos初始化管理类
 * Created by dingqq on 2018/7/17.
 */

public class AvosManager {

    //推送action
    public static String mPushAction = "";

    //推送回调
    private static List<PushCallBack> mBacks = new ArrayList<>();

    /**
     * avos 初始化
     *
     * @param context    上下文
     * @param appId      申请的AppId
     * @param appKey     申请的AppKey
     * @param appChannel 渠道
     */
    public static void initAvos(Context context, String appId, String appKey, String appChannel) {

        mPushAction = context.getPackageName() + ".pushReceiver";

        //是否上送数据
        AVAnalytics.setAnalyticsEnabled(true);

        //设置渠道
        AVAnalytics.setAppChannel(appChannel);

        //开启Debug模式
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);

        //节省查询流量
        AVOSCloud.setLastModifyEnabled(true);

        //初始化
        AVOSCloud.initialize(context, appId, appKey);

        //崩溃信息收集
        AVAnalytics.enableCrashReport(context, true);
    }

    public EventManager getEventManager() {
        return EventManager.getInstance();
    }

    /**
     * 初始化推送
     *
     * @param context  上下文
     * @param clazz    设置默认打开的 Activity
     * @param callBack InstallationId回调
     */
    public static void initPush(Context context, Class<? extends Activity> clazz,
                                final PushCallBack callBack) {

        //初始化消息推送
        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e != null) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onBack("");
                    }
                    return;
                }

                if (callBack != null) {
                    callBack.onBack(AVInstallation.getCurrentInstallation().getInstallationId());
                }
            }
        });

        PushService.setDefaultPushCallback(context, clazz);
    }

    public static void unregisterPushListener(PushCallBack pushCallBack) {
        synchronized (AvosManager.class) {
            mBacks.remove(pushCallBack);
        }
    }

    public static void registerPushListener(PushCallBack pushCallBack) {
        synchronized (AvosManager.class) {
            mBacks.add(pushCallBack);
        }
    }

    public static void removeAllPushListener() {
        synchronized (AvosManager.class) {
            mBacks.clear();
        }
    }

    public static List<PushCallBack> getBacks() {
        return mBacks;
    }

    public interface PushCallBack {
        void onBack(String text);
    }
}
