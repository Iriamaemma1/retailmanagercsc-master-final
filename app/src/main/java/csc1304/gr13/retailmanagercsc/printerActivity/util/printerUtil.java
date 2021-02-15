package csc1304.gr13.retailmanagercsc.printerActivity.util;

import android.util.Log;

public class printerUtil {
    private static String TAG = "Util";

    public static String fixCardNoWithMask(String card) {
        Log.d(TAG, "try fixCardNoWithMask:" + card);
        if (card == null) {
            return null;
        }
        String fix = card;
        int start = 6;
        if (6 < 0) {
            start = 6 + card.length();
        }
        int end = -4;
        if (-4 < 0) {
            end = -4 + card.length();
        }
        Log.d(TAG, "replace mask * from " + start + " to " + end);
        if (start > end) {
            return card;
        }
        String fix2 = card.substring(0, start);
        for (int i = start; i < end; i++) {
            fix2 = fix2 + "*";
        }
        String fix3 = fix2 + card.substring(end);
        Log.d(TAG, "try insert space to card No. " + fix3);
        String fix22 = "";
        int i2 = 0;
        while (i2 < fix3.length()) {
            int end2 = i2 + 4;
            if (end2 > fix3.length()) {
                end2 = fix3.length();
            }
            fix22 = fix22 + fix3.substring(i2, end2);
            if (end2 < fix3.length()) {
                fix22 = fix22 + " ";
            }
            i2 = end2;
        }
        return fix22;
    }

    public static String fixCardNoWithMaskNoSpace(String card) {
        Log.d(TAG, "try fixCardNoWithMask:" + card);
        if (card == null) {
            return null;
        }
        String fix = card;
        int start = 6;
        if (6 < 0) {
            start = 6 + card.length();
        }
        int end = -4;
        if (-4 < 0) {
            end = -4 + card.length();
        }
        Log.d(TAG, "replace mask * from " + start + " to " + end);
        if (start > end) {
            return card;
        }
        String fix2 = card.substring(0, start);
        for (int i = start; i < end; i++) {
            fix2 = fix2 + "*";
        }
        return fix2 + card.substring(end);
    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }
}
