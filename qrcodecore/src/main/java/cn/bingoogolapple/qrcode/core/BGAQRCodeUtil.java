package cn.bingoogolapple.qrcode.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class BGAQRCodeUtil {

    public static Point getScreenResolution(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenResolution = new Point();
        if (android.os.Build.VERSION.SDK_INT >= 13) {
            display.getSize(screenResolution);
        } else {
            screenResolution.set(display.getWidth(), display.getHeight());
        }
        return screenResolution;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static Bitmap adjustPhotoRotation(Bitmap inputBitmap, int orientationDegree) {
        Matrix matrix = new Matrix();
        matrix.setRotate(orientationDegree, (float) inputBitmap.getWidth() / 2, (float) inputBitmap.getHeight() / 2);
        float outputX, outputY;
        if (orientationDegree == 90) {
            outputX = inputBitmap.getHeight();
            outputY = 0;
        } else {
            outputX = inputBitmap.getHeight();
            outputY = inputBitmap.getWidth();
        }

        final float[] values = new float[9];
        matrix.getValues(values);
        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];
        matrix.postTranslate(outputX - x1, outputY - y1);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap.getHeight(), inputBitmap.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(outputBitmap);
        canvas.drawBitmap(inputBitmap, matrix, paint);
        return outputBitmap;
    }
}