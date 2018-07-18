package com.lakala.cloudpos.avoslibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.List;

/**
 * 消息推送接送类
 * Created by dingqq on 2018/7/17.
 */

public class CusPushReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        //推送的action
        if (AvosManager.mPushAction.equals(action)) {
            //获取消息内容
            String data = intent.getExtras().getString("com.avos.avoscloud.Data");

            List<AvosManager.PushCallBack> list = AvosManager.getBacks();
            int size = list.size();

            if (size > 0) {
                for (int i = size - 1; i >= 0; i--) {
                    AvosManager.PushCallBack callBack = list.get(i);
                    if (callBack != null) {
                        callBack.onBack(data);
                    } else {
                        list.remove(i);
                    }
                }
            }

        }
    }

}
