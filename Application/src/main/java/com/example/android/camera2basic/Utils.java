package com.example.android.camera2basic;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by cdesai on 2/13/18.
 */

public class Utils {

    private static final String TAG = "Utils";
    private static Rect framingRect;
    private static Rect framingRectInPreview;

    /**
     * Calculates the framing rect which the UI should draw to show the user where to place the
     * barcode. This target helps with alignment as well as forces the user to hold the device
     * far enough away to ensure the image will be in focus.
     *
     * @return The rectangle to draw on screen in window coordinates.
     */
    public static synchronized Rect getFramingRect() {
        if (framingRect == null) {
            Point screenResolution = getScreenResolution();

            int width = getFramingRectSize();

            int leftOffset = (screenResolution.x - width) / 2;
            int topOffset = (screenResolution.y - width) / 2;
            framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + width);
            Log.d(TAG, "cdesai: Calculated framing rect: " + framingRect);
        }
        return framingRect;
    }

    //Calculated framing rect: Rect(740, 180 - 1820, 1260)
    //Calculated framing RectInPreview: Rect(555, 135 - 1365, 945)

    //Calculated framing rect in preview: Rect(526, 135 - 1393, 945)

    /**
     * Like {@link #getFramingRect} but coordinates are in terms of the preview frame,
     * not UI / screen.
     *
     * @return {@link Rect} expressing barcode scan area in terms of the preview size
     */
    public static synchronized Rect getFramingRectInPreview() {
        if (framingRectInPreview == null) {
            Rect framingRect = getFramingRect();
            if (framingRect == null) {
                return null;
            }
            Rect rect = new Rect(framingRect);
            Point screenResolution = getScreenResolution();
            Point cameraResolution = getCameraResolution();
            rect.left = rect.left * cameraResolution.x / screenResolution.x;
            rect.right = rect.right * cameraResolution.x / screenResolution.x;
            rect.top = rect.top * cameraResolution.y / screenResolution.y;
            rect.bottom = rect.bottom * cameraResolution.y / screenResolution.y;
            framingRectInPreview = rect;
            Log.d(TAG, "cdesai: Calculated framing RectInPreview: " + framingRectInPreview);
        }
        return framingRectInPreview;
    }

    public static int getFramingRectSize() {
        Point screenRes = getScreenResolution();
        int width = 3 * screenRes.y / 4;
        return width;
    }

    public static Point getScreenResolution() {
        // TODO
        return new Point(2560, 1440);
    }

    public static Point getCameraResolution() {
        // TODO
        return new Point(1920, 1080);
    }
}
