package com.lueinfo.bshop.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class Utils {
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
				connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
			return true;
		}else{
			return false;
		}

	}
	public static boolean isGpsEnable(Context context){
		LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
		boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (statusOfGPS==true){
		return true;
		}else{
			return false;
		}
	}
	public static Bitmap decodeSampledBitmapFromPath(String pathName, int reqWidth, int reqHeight) {
	     // First decode with inJustDecodeBounds=true to check dimensions
	     final BitmapFactory.Options options = new BitmapFactory.Options();
	     options.inJustDecodeBounds = true;
	     BitmapFactory.decodeFile(pathName, options);

	     // Calculate inSampleSize
	     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	     // Decode bitmap with inSampleSize set
	     options.inJustDecodeBounds = false;
	     return BitmapFactory.decodeFile(pathName, options);
	 }
	 public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
	     // Raw height and width of image
	     final int height = options.outHeight;
	     final int width = options.outWidth;
	     int inSampleSize = 1;
	 
	     if (height > reqHeight || width > reqWidth) {
	         if (width > height) {
	             inSampleSize = Math.round((float)height / (float)reqHeight);
	         } else {
	             inSampleSize = Math.round((float)width / (float)reqWidth);
	         }
	     }
	     return inSampleSize;
	 }
    public static void cartAnimation(View vanim){

		//cart animate
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
		scale.setDuration(1000);
		scale.setInterpolator(new OvershootInterpolator());
		vanim.startAnimation(scale);
	}
	public static void Animationviewtoview(Activity activity, View v1, View v2){

		int[] screenLocation = new int[2];
		v1.getLocationOnScreen(screenLocation);
		int startX = screenLocation[0];
		int startY = screenLocation[1];

		int[] screenLocationB = new int[2];
		v2.getLocationOnScreen(screenLocationB);
		int endX = screenLocationB[0];
		int endY = screenLocationB[1];

		//here we add to the window layer:
		WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
		layoutParams.width = v2.getMeasuredWidth();
		layoutParams.height = v2.getMeasuredHeight();
		//((ViewGroup)v2.getParent()).removeView(v2);
		TextView newtv=new TextView(activity);
		newtv.setText("1");
	/*	newtv.setTextColor(activity.getResources().getColor(android.R.color.holo_blue_light));
		newtv.setBackgroundResource(R.drawable.bucket);
		newtv.setGravity(Gravity.CENTER);*/
		activity.addContentView(v2, layoutParams);
		// Next we set the starting position.
		newtv.setTranslationX(startX);
		newtv.setTranslationY(startY);

		//Finally we animate.
		newtv.animate().setDuration(1200)
				.translationX(endX).translationY(endY).start();
		//cartAnimation(v2);
	}

}
