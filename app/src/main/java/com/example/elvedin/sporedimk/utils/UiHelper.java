package com.example.elvedin.sporedimk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.elvedin.sporedimk.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

/**
 * Created by Elve on 7/31/2017.
 */

public class UiHelper {

    public static Dialog createLoadingDialog(Context ctx) {
        View v = View.inflate(ctx, R.layout.custom_progress_dialog, null);

        Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(v);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Animation rotation = AnimationUtils.loadAnimation(ctx, R.anim.rotate_anim);
        rotation.setFillAfter(true);
        v.startAnimation(rotation);

        return dialog;
    }

    @SuppressLint("NewApi")
    public static Bitmap BlurImage(Bitmap input, Context context) {
        try {
            RenderScript rsScript = RenderScript.create(context);
            Allocation alloc = Allocation.createFromBitmap(rsScript, input);

            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rsScript, Element.U8_4(rsScript));
            blur.setRadius(21);
            blur.setInput(alloc);

            Bitmap result = Bitmap.createBitmap(input.getWidth(), input.getHeight(), Bitmap.Config.ARGB_8888);
            Allocation outAlloc = Allocation.createFromBitmap(rsScript, result);

            blur.forEach(outAlloc);
            outAlloc.copyTo(result);

            rsScript.destroy();
            return result;
        } catch (Exception e) {
            // TODO: handle exception
            return input;
        }

    }

    public static void clearBackstackToHome(FragmentManager fm) {
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        DatePicker datePicker;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePicker = datePickerDialog.getDatePicker();
            // Create a new instance of DatePickerDialog and return it
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }

        public void setMinDate(Calendar calendar) {
            DatePickerDialog datePickerDialog = (DatePickerDialog) this.getDialog();
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }
    }

    public static boolean validateEmpty(EditText editText) {
        return editText.getText().toString().length() > 0;
    }

    public static boolean validateDecimalNumbers(EditText editText) {
        if (editText.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateIntegerNumbers(EditText editText) {
        if (editText.getText().toString().matches("\\d+")) {
            return true;
        } else {
            return false;
        }
    }

    public static void setError(Context context, View view, String text) {
        if (view instanceof EditText) {
            view.clearFocus();
            ((EditText) view).setText(text);
        }
    }


    public static String printKeyHash(Activity activity) {
        String keyHash = "";
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "io.medialab.sttb",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHash);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyHash;
    }

    public static String printSHA1(Activity activity) {
        String keyHash = "";
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    "io.medialab.sttb",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHash);
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyHash;
    }

    public static void addFragment(FragmentManager fm, int layout, Fragment fragment, String tag , boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if (enterAnimation != 0 || exitAnimation != 0) {
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.add(layout, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    public static void replaceFragment(FragmentManager fm, int layout, Fragment fragment, boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if (enterAnimation != 0 || exitAnimation != 0) {
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.replace(layout, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    public static void replaceFragment(FragmentManager fm, int layout, Fragment fragment, String tag, boolean addToBackStack, int enterAnimation, int exitAnimation) {
        FragmentTransaction ft = fm.beginTransaction();
        if (enterAnimation != 0 || exitAnimation != 0) {
            ft.setCustomAnimations(enterAnimation, exitAnimation, enterAnimation, exitAnimation);
        }
        ft.replace(layout, fragment, tag);
        if (addToBackStack) {
            ft.addToBackStack(tag);
        }
        ft.commit();
    }

    public static void clearBackstack(FragmentManager fm) {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void popUpBackstack(FragmentManager fm) {
        fm.popBackStackImmediate();
    }


}
