package com.zxy.functioncollection.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.zxy.functioncollection.MainActivity;
import com.zxy.functioncollection.MyApplication;

import java.io.Closeable;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("SimpleDateFormat")
public class CommonUtil {
    
    public final static String DIR_PATH = "/cuocuoxia/";

	public static Resources sRes = MyApplication.getInstance().getResources();

	public static int dip2px(float dpValue) {
		final float scale = sRes.getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(float pxValue) {
		final float scale = sRes.getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static String getString(int resId) {
		return MyApplication.getInstance().getResources().getString(resId);
	}

	public static int getColor(int resId) {
		return MyApplication.getInstance().getResources().getColor(resId);
	}

	/**
	 * 格式化时间范围.
	 * 
	 * @return
	 */
	public static String formatDateRange(long dateStart, long dateEnd) {
		final SimpleDateFormat sdfStart = new SimpleDateFormat("MM月dd日 HH:mm");
		final SimpleDateFormat sdfEnd = new SimpleDateFormat("HH:mm");
		final StringBuilder sb = new StringBuilder();
		final Date ds = new Date(dateStart * 1000);
		final Date de = new Date(dateEnd * 1000);
		sb.append(sdfStart.format(ds)).append(" 至 ").append(sdfEnd.format(de));
		return sb.toString();
	}

	/**
	 * 是否为今天.
	 * 
	 * @param time
	 * @return
	 */
	public static boolean isToday(long time) {
		// 昨天
		Calendar yCalendar = Calendar.getInstance();
		yCalendar.add(Calendar.DAY_OF_MONTH, -1);
		yCalendar.set(Calendar.HOUR_OF_DAY, 24);
		yCalendar.set(Calendar.MINUTE, 0);
		yCalendar.set(Calendar.SECOND, 0);

		// 今天
		Calendar tCalendar = Calendar.getInstance();
		tCalendar.add(Calendar.DAY_OF_MONTH, 1);
		tCalendar.set(Calendar.HOUR_OF_DAY, 0);
		tCalendar.set(Calendar.MINUTE, 0);
		tCalendar.set(Calendar.SECOND, 0);

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));
		if (c.after(yCalendar) && c.before(tCalendar)) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串日期专秒.
	 * 
	 * @param date
	 * @return
	 */
	public static long getDateSecond(String date) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date d = format.parse(date);
			return d.getTime() / 1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 格式化时间.
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(String date) {
		final SimpleDateFormat format1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format2.format(format1.parse(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 显示屏幕分辨率.
	 * 
	 */
	public static void showPixels(Activity activity) {
		final DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		final String msg = "widthPixels=" + dm.widthPixels + "\nheightPixels="
				+ dm.heightPixels + "\ndensity" + dm.density;
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 将double转化成保留1位小树的字符串.
	 * 
	 * @param value
	 */
	public static float numberFormat(double value) {
		final DecimalFormat format = new DecimalFormat("##0.00");
		return Float.parseFloat(format.format(value));
	}

	/**
	 * 获取当前版本信息.
	 * 
	 * @param context
	 */
	public static String getCurrentVersion(Context context) {
		String versionName = "";
		try {
			final PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0);
			versionName = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 判断Wi-Fi是否可用
	 * 
	 * @param context
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			final ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			final NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
	 /**
     * 检查当前网络是否可用
     * 
     * @param context
     * @return
     */
    
    public static boolean isNetworkAvailable(Context context)
    {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            
            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
//                    System.out.println(i + "===状态===" + networkInfo[i].getState());
//                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
	/**
	 * 获取metaData
	 * 
	 * @return
	 */
	public static String getMetaData(Context context) {
		try {
			ApplicationInfo appInfo = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 验证是否为手机号码.
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		String telRegex = "[1][358]\\d{9}";
		if (TextUtils.isEmpty(phone)) {
			return false;
		}
		return phone.matches(telRegex);
	}

	// 判断email格式是否正确
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static void closeStream(Closeable stream) {
		if (null != stream) {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断MainActivity是否打开.
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isMainActivityOpen(Activity context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		try {
			List<RunningTaskInfo> rtiList = manager.getRunningTasks(1);
			boolean isOpen = MainActivity.class.getCanonicalName().equals(
					rtiList.get(0).baseActivity.getClassName());
			return isOpen;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int getListViewHeight(ListView listView) {
		int totalHeight = 0;
		ListAdapter listAdapter = listView.getAdapter();
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			int list_child_item_height = listItem.getMeasuredHeight()
					+ listView.getDividerHeight();
			totalHeight += list_child_item_height;
		}
		return totalHeight;
	}

	/**
	 * 设置ViewGroup整个选中状态.
	 * 
	 * @param vg
	 * @param selected
	 */
	public static void setViewGroupState(ViewGroup vg, boolean selected) {
		for (int i = 0; i < vg.getChildCount(); i++) {
			vg.getChildAt(i).setSelected(selected);
		}
	}

	// 把数字的字符串转为三位加逗号
	public static String fixRewordText(String oldStr) {
		oldStr = new StringBuilder(oldStr).reverse().toString();
		String newStr = "";

		for (int i = 0; i < oldStr.length(); i++) {
			if (i * 3 + 3 > oldStr.length()) {
				newStr += oldStr.substring(i * 3, oldStr.length());
				break;
			}
			newStr += oldStr.substring(i * 3, i * 3 + 3) + ",";
		}

		if (newStr.endsWith(",")) {
			newStr = newStr.substring(0, newStr.length() - 1);
		}

		return new StringBuilder(newStr).reverse().toString();
	}

	/*
	 * 判断邮箱格式是否正确
	 */

	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	public static int getScreenWidth() {
		return sRes.getDisplayMetrics().widthPixels;
	}

	public static int getScreenHeight() {
		return sRes.getDisplayMetrics().heightPixels;
	}

}
