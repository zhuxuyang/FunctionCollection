package com.zxy.functioncollection.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
	public static String getFilePathByUri(Context context, Uri uri) {
		String path = null;
		if ("file".equals(uri.getScheme())) {
			path = uri.getEncodedPath();
			if (path != null) {
				path = Uri.decode(path);
				ContentResolver cr = context.getContentResolver();
				StringBuffer buff = new StringBuffer();
				buff.append("(").append(Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
				Cursor cur = cr.query(Images.Media.EXTERNAL_CONTENT_URI, new String[] { Images.ImageColumns._ID, Images.ImageColumns.DATA }, buff.toString(), null, null);
				int index = 0;
				int dataIdx = 0;
				for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
					index = cur.getColumnIndex(Images.ImageColumns._ID);
					index = cur.getInt(index);
					dataIdx = cur.getColumnIndex(Images.ImageColumns.DATA);
					path = cur.getString(dataIdx);
				}
				cur.close();
				if (index == 0) {
				} else {
					Uri u = Uri.parse("content://media/external/images/media/" + index);
					// System.out.println("temp uri is :" + u);
				}
			}
			if (path != null) {
				return path;
			}
		} else if ("content".equals(uri.getScheme())) {
			// 4.2.2以后
			String[] proj = { Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndexOrThrow(Images.Media.DATA);
				path = cursor.getString(columnIndex);
			}
			cursor.close();

			return path;
		}
		return null;
	}

	public static byte[] File2byte(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}


}
