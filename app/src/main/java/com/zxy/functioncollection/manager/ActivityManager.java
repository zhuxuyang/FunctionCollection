package com.zxy.functioncollection.manager;

import android.app.Activity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Administrator on 2017/2/15 0015.
 * 用栈和Map来管理用户操作过程中产生的Activity，包括记录、查询和销毁。
 *
 * 它的作用不是用于取代系统对Activity的管理，而是我们一个Tab（TabActivity）内<b>形式上</b>可能包含多个Activity这样一个需求
 * 需要我们自己额外记录Activity的实例，以便需要的时候可以取出来用。
 *
 */

public class ActivityManager {
    private Stack<Activity> mActivityStack = new Stack<Activity>();
    private Map<Activity, String> mActivityTagMap = new HashMap<Activity, String>();

    private static ActivityManager instance;

    private ActivityManager() {
    }

    synchronized public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            mActivityTagMap.remove(activity);
            activity = null;
        }
    }

    public void addActivity(Activity activity) {
        if (activity == null)
            return;

        mActivityStack.push(activity);
    }

    /**
     * 给已经入栈的Activity加标签。
     *
     * @param activity
     *            该activity必须是已经入栈，否则不会为其添加标签
     * @param activityTag
     *            是否添加了标签
     * @return
     */
    public boolean setActivityTag(Activity activity, String activityTag) {
        if (mActivityStack.contains(activity)) {
            mActivityTagMap.put(activity, activityTag);
            return true;
        }

        return false;
    }

    // get all of activity
    public Stack<Activity> getAllRegisterActivity() {
        return mActivityStack;
    }

    public Activity getActivityByTag(String tag) {
        Iterator<Map.Entry<Activity, String>> mapIt = mActivityTagMap.entrySet().iterator();
        while (mapIt.hasNext()) {
            Map.Entry<Activity, String> it = mapIt.next();
            if (it.getValue().equals(tag)) {
                return it.getKey();
            }
        }

        return null;
    }

    public void FinishAllActivity() {
        Activity finishActivity = null;
        while (mActivityStack.size() > 0) {
            finishActivity = mActivityStack.pop();
            if (finishActivity != null) {
                finishActivity.finish();
                finishActivity = null;
            }
        }
        mActivityTagMap.clear();
    }

    public void FinishAllActivityByTag(List<String> list) {
        Activity finishActivity = null;
        for (int i = 0; i < list.size(); i++) {
            finishActivity = getActivityByTag(list.get(i));
            if (finishActivity != null) {
                finishActivity.finish();
                finishActivity = null;
            }
        }
    }

    public int getActivityCount() {
        return mActivityStack.size();
    }
}
