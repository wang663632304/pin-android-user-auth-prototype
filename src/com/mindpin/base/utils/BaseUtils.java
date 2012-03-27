package com.mindpin.base.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.mindpin.application.MindpinApplication;

public class BaseUtils {
	
	
	public static int dp_to_px(int dip) {
		Resources r = MindpinApplication.context.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
	}
	
	public static String date_string(long time){
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
	
	// yyyy-MM-ddTHH:mm:ssZ
	public static long parse_iso_time_string_to_long(String iso_time_string) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd'T'HH:mm:ssZ");
		return sdf.parse(iso_time_string).getTime();
	}
	
	public static boolean is_wifi_active(Context context) {
		Context acontext = context.getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) acontext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getTypeName().equals("WIFI")
							&& info[i].isConnected()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	// [1,2,3,4] -> "1,2,3,4"
	public static String integer_list_to_string(List<Integer> ids){
		String res = "";
		if(ids !=null){
			for (Integer s : ids){
				if("".equals(res)){
					res = res + s;
				}else{
					res = res + "," + s;
				}
			}
		}
		return res;
	}
	
	// ["1","2","3","4"] -> "1,2,3,4"
	public static String string_list_to_string(List<String> strs){
		String res="";
		if(strs !=null){
			for (String s : strs){
				if("".equals(res)){
					res = res + s;
				}else{
					res = res + "," + s;
				}
			}
		}
		return res;
	}
	
	public static List<String> string_to_string_list(String string){
		List<String> list = new ArrayList<String>();
		String[] arr = string.split(",");
		for (String str : arr) {
			if(!"".equals(str)){
				list.add(str);
			}
		}
		return list;
	}
	
	public static List<Integer> string_to_integer_list(String string){
		List<Integer> list = new ArrayList<Integer>();
		String[] arr = string.split(",");
		for (String str : arr) {
			if(!"".equals(str)){
				list.add(Integer.parseInt(str));
			}
		}
		return list;		
	}

    // 把字节流转换成字符串
	public static String convert_stream_to_string(InputStream is) {
		if (is != null) {
			Writer writer = new StringWriter();
	
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} catch (Exception e) {
				return "";
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }
    
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    
    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024 * 4];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    
    //判断字符串非空
    public static boolean is_str_blank(String str) {
    	int length;

    	if ((str == null) || ((length = str.length()) == 0)) {
    		return true;
    	}

    	for (int i = 0; i < length; i++) {
    		if (!Character.isWhitespace(str.charAt(i))) {
    			return false;
    		}
    	}
    	return true;
    }
    
    // 快速显示一个toast
    public static void toast(int string_resource_id){
    	Toast toast = Toast.makeText(
    		MindpinApplication.context, 
			string_resource_id,
			Toast.LENGTH_SHORT
		);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	toast.show();
    }
    
    public static void toast(String string){
    	Toast toast = Toast.makeText(
    		MindpinApplication.context, 
			string,
			Toast.LENGTH_SHORT
		);
    	toast.setGravity(Gravity.CENTER, 0, 0);
    	toast.show();
    }
    
    public static Bitmap to_round_corner(Bitmap bitmap, int pixels) { 
        
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888); 
        Canvas canvas = new Canvas(output); 
 
        final int color = 0xff424242; 
        final Paint paint = new Paint(); 
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
        final RectF rectF = new RectF(rect); 
        final float roundPx = pixels; 
 
        paint.setAntiAlias(true); 
        canvas.drawARGB(0, 0, 0, 0); 
        paint.setColor(color); 
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
 
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
        canvas.drawBitmap(bitmap, rect, rect, paint); 
 
        return output; 
    }
    
    public static String location_to_string(Location location){
    	if(location == null){
    		return "";
    	}
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		return lat + "," + lng;
    }
    
}
