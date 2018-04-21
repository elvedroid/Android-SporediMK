package org.emobile.myitmarket.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CustomFunctions {

    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1;

    public static void setDialogFullScreen(Dialog dialog, Activity act) {
        if (dialog != null) {
            Rect rectangle = new Rect();
            Window window = act.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            Point displayPoint = new Point();
            act.getWindow().getWindowManager().getDefaultDisplay().getSize(displayPoint);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            if (dialog.getWindow() != null) {
                dialog.getWindow().setLayout(width, height);
                // make dialog itself transparent
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                // remove background dim
                dialog.getWindow().setDimAmount(0);
            }
        }
    }


    public static void setDialogFullScreenTransparent(Dialog dialog, Activity act) {
        if (dialog != null && dialog.getWindow() != null) {
            Rect rectangle = new Rect();
            Window window = act.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            Point displayPoint = new Point();
            act.getWindow().getWindowManager().getDefaultDisplay().getSize(displayPoint);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            // make dialog itself transparent
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // remove background dim
            dialog.getWindow().setDimAmount(0);
        }
    }


    public static void setDialogFullScreen(Activity act) {
        // make dialog itself transparent
        act.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        // remove background dim
        act.getWindow().setDimAmount(0);
    }

    public static class DownloadBitmapFromURL extends AsyncTask<URL, Integer, Bitmap> {
        ImageView image;

        public DownloadBitmapFromURL(ImageView image) {
            this.image = image;
        }

        protected Bitmap doInBackground(URL... urls) {
            try {
                URL url = urls[0];
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                // Log exception
                return null;
            }
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Bitmap result) {
            image.setImageBitmap(result);
        }
    }

    private static boolean isGpsActive(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private static boolean isNetworkLocationActive(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(View view, Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public static boolean checkPlayServices(Activity activity) {
//        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//                return false;
//            } else {
//                return false;
//            }
//        }
        return true;
    }


    public static boolean isLocationEnabled(Activity activity) {
        return isGpsActive(activity) || isNetworkLocationActive(activity);
    }


    public static String encodeBitmapTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static String toBase64(File file, int qualityIndex) {
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        return toBase64(bm, qualityIndex);
    }

    private static final int RESIZE_WIDTH = 1500;
    private static final int RESIZE_HEIGHT = 1500;

    public static String toBase64(Bitmap bm, int qualityIndex) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bm.getWidth(), bm.getHeight()), new RectF(0, 0, RESIZE_WIDTH, RESIZE_HEIGHT), Matrix.ScaleToFit.CENTER);
        Bitmap resized = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resized.compress(Bitmap.CompressFormat.JPEG, qualityIndex, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.NO_WRAP);
    }

    public static Bitmap toBitmap(String encodedBse64) {
        if (encodedBse64 != null) {
            byte[] decodedByte = Base64.decode(encodedBse64, Base64.NO_WRAP);
            return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }
        return null;
    }

    public static String getFormattedPrice(Double price) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(price) + ",00";
    }

    public static String getStringFromDateLong(Long date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return simpleDateFormat.format(calendar.getTime());
    }
}
