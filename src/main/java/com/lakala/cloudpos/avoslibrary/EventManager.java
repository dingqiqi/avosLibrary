package com.lakala.cloudpos.avoslibrary;

import android.content.Context;

import com.avos.avoscloud.AVAnalytics;

import java.util.Map;

/**
 * avos界面统计及事件
 * Created by dingqq on 2018/7/17.
 */

public class EventManager {

    private EventManager() {
    }

    public static EventManager getInstance() {
        return EventInstance.mInstance;
    }

    private static class EventInstance {
        private final static EventManager mInstance = new EventManager();
    }

    /**
     * 统计界面暂停
     *
     * @param context 上下文
     */
    public static void onPause(Context context) {
        AVAnalytics.onPause(context);
    }

    /**
     * 统计界面运行
     *
     * @param context 上下文
     */
    public static void onResume(Context context) {
        AVAnalytics.onResume(context);
    }

    /**
     * 例如:定义一次点击事件
     *
     * @param context 上下文
     * @param eventId 事件名称
     */
    public static void onEvent(Context context, String eventId) {
        AVAnalytics.onEvent(context, eventId);
    }

    /**
     * 例如:定义一次点击事件
     *
     * @param context 上下文
     * @param eventId 事件名称
     * @param tag     事件里面的具体行为
     */
    public static void onEvent(Context context, String eventId, String tag) {
        AVAnalytics.onEvent(context, eventId, tag);
    }

    /**
     * 例如:定义一次点击事件
     *
     * @param context 上下文
     * @param eventId 事件名称
     * @param data    上送数据
     */
    public static void onEvent(Context context, String eventId, Map<String, String> data) {
        AVAnalytics.onEvent(context, eventId, data);
    }

}
