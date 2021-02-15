package csc1304.gr13.retailmanagercsc.printerActivity.util;



import android.annotation.SuppressLint;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class Utils {
    private static int findTagLength;
    private static byte[] findTagValue;
    private static byte[] findedTag;

    public static String bcd2Asc(byte[] bcd) {
        String str;
        if (bcd == null || bcd.length <= 0) {
            return null;
        }
        String str2 = "";
        try {
            StringBuilder sb = new StringBuilder("");
            for (byte b : bcd) {
                String stmp = Integer.toHexString(b & 255);
                if (stmp.length() == 1) {
                    str = "0" + stmp;
                } else {
                    str = stmp;
                }
                sb.append(str);
            }
            return sb.toString().toUpperCase().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static String bcd2Asc(byte[] bcd, int length) {
        String str;
        if (bcd == null || bcd.length <= 0 || length <= 0 || length > bcd.length) {
            return null;
        }
        String str2 = "";
        try {
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < length; i++) {
                String stmp = Integer.toHexString(bcd[i] & 255);
                if (stmp.length() == 1) {
                    str = "0" + stmp;
                } else {
                    str = stmp;
                }
                sb.append(str);
            }
            return sb.toString().toUpperCase().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static String bcd2Str(byte[] bcd) {
        if (bcd == null || bcd.length <= 0) {
            return null;
        }
        char[] ascii = "0123456789abcdef".toCharArray();
        byte[] temp = new byte[(bcd.length * 2)];
        int i = 0;
        while (i < bcd.length) {
            try {
                temp[i * 2] = (byte) ((bcd[i] >> 4) & 15);
                temp[(i * 2) + 1] = (byte) (bcd[i] & 15);
                i++;
            } catch (Exception e) {
                return null;
            }
        }
        StringBuffer res = new StringBuffer();
        for (byte b : temp) {
            res.append(ascii[b]);
        }
        return res.toString().toUpperCase();
    }

    public static byte[] asc2Bcd(String asc) {
        if (asc == null) {
            return null;
        }
        return asc2Bcd(asc, asc.length());
    }

    public static byte[] asc2Bcd(String asc, int length) {
        int j;
        int k;
        if (asc == null || asc.length() <= 0 || length <= 0 || length > asc.length()) {
            return null;
        }
        int len = length;
        try {
            if (len % 2 != 0) {
                asc = "0" + asc;
                len++;
            }
            byte[] bArr = new byte[len];
            if (len >= 2) {
                len /= 2;
            }
            byte[] bbt = new byte[len];
            byte[] abt = asc.getBytes();
            for (int p = 0; p < len; p++) {
                if (abt[p * 2] >= 48 && abt[p * 2] <= 57) {
                    j = abt[p * 2] - 48;
                } else if (abt[p * 2] < 97 || abt[p * 2] > 122) {
                    j = (abt[p * 2] - 65) + 10;
                } else {
                    j = (abt[p * 2] - 97) + 10;
                }
                if (abt[(p * 2) + 1] >= 48 && abt[(p * 2) + 1] <= 57) {
                    k = abt[(p * 2) + 1] - 48;
                } else if (abt[(p * 2) + 1] < 97 || abt[(p * 2) + 1] > 122) {
                    k = (abt[(p * 2) + 1] - 65) + 10;
                } else {
                    k = (abt[(p * 2) + 1] - 97) + 10;
                }
                bbt[p] = (byte) ((j << 4) + k);
            }
            return bbt;
        } catch (Exception e) {
            return null;
        }
    }

    public static String byte2HexStr(byte[] data) {
        String str;
        if (data == null || data.length <= 0) {
            return null;
        }
        String str2 = "";
        try {
            StringBuilder sb = new StringBuilder("");
            for (byte b : data) {
                String stmp = Integer.toHexString(b & 255);
                if (stmp.length() == 1) {
                    str = "0" + stmp;
                } else {
                    str = stmp;
                }
                sb.append(str);
            }
            return sb.toString().toUpperCase().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public static String byte2HexStr(byte[] data, int len) {
        String str;
        if (data == null || data.length <= 0 || len <= 0 || len > data.length) {
            return null;
        }
        String str2 = "";
        try {
            StringBuilder sb = new StringBuilder("");
            for (int n = 0; n < len; n++) {
                String stmp = Integer.toHexString(data[n] & 255);
                if (stmp.length() == 1) {
                    str = "0" + stmp;
                } else {
                    str = stmp;
                }
                sb.append(str);
            }
            return sb.toString().toUpperCase().trim();
        } catch (Exception e) {
            return null;
        }
    }

    private String byte2HexStr(byte[] data, int offset, int len) {
        String str = null;
        if (data == null || data.length <= 0 || offset < 0 || len <= 0 || offset > data.length || len > data.length - offset) {
            return str;
        }
        try {
            return byte2HexStr(subBytes(data, offset, len));
        } catch (Exception e) {
            return str;
        }
    }

    public static String byte2Str(byte[] byteData) {
        if (byteData == null || byteData.length <= 0) {
            return null;
        }
        try {
            StringBuilder buf = new StringBuilder();
            for (byte b : byteData) {
                String tempStr = Integer.toHexString(b & 255);
                if (tempStr.length() == 1) {
                    buf.append("0").append(tempStr);
                } else {
                    buf.append(tempStr);
                }
            }
            return buf.toString().toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] hexStr2Bytes(String src) {
        if (src == null || src.length() <= 0) {
            return null;
        }
        try {
            if (src.length() % 2 != 0) {
                src = "0" + src;
            }
            int l = src.length() / 2;
            byte[] ret = new byte[l];
            for (int i = 0; i < l; i++) {
                int m = (i * 2) + 1;
                ret[i] = Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, m + 1)).byteValue();
            }
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static String hexStr2Str(String hexStr) {
        if (hexStr == null || hexStr.length() <= 0) {
            return null;
        }
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[(hexStr.length() / 2)];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (((str.indexOf(hexs[i * 2]) * 16) + str.indexOf(hexs[(i * 2) + 1])) & 255);
        }
        try {
            return new String(bytes, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String str2Asc(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        String ascStr = "";
        for (int i = 0; i < str.length(); i++) {
            ascStr = ascStr + String.format("%02X", new Object[]{Byte.valueOf(str.substring(i, i + 1).getBytes()[0])});
        }
        return ascStr;
    }

    public static String asc2str(String asc, int length) {
        int j;
        int k;
        if (asc == null || asc.length() <= 0 || length <= 0 || length > asc.length()) {
            return null;
        }
        int len = length;
        try {
            if (len % 2 != 0) {
                asc = "0" + asc;
                len++;
            }
            byte[] bArr = new byte[len];
            if (len >= 2) {
                len /= 2;
            }
            String bbt = "";
            byte[] abt = asc.getBytes();
            for (int p = 0; p < len; p++) {
                if (abt[p * 2] >= 48 && abt[p * 2] <= 57) {
                    j = abt[p * 2] - 48;
                } else if (abt[p * 2] < 97 || abt[p * 2] > 122) {
                    j = (abt[p * 2] - 65) + 10;
                } else {
                    j = (abt[p * 2] - 97) + 10;
                }
                if (abt[(p * 2) + 1] >= 48 && abt[(p * 2) + 1] <= 57) {
                    k = abt[(p * 2) + 1] - 48;
                } else if (abt[(p * 2) + 1] < 97 || abt[(p * 2) + 1] > 122) {
                    k = (abt[(p * 2) + 1] - 65) + 10;
                } else {
                    k = (abt[(p * 2) + 1] - 97) + 10;
                }
                int a = (j << 4) + k;
                bbt = bbt + String.format("%02X", new Object[]{Integer.valueOf(a)});
            }
            return bbt;
        } catch (Exception e) {
            return null;
        }
    }

    public static int bcd2Int(byte[] bcd) {
        int i = 0;
        if (bcd == null || bcd.length <= 0) {
            return i;
        }
        try {
            return Integer.parseInt(bcd2Asc(bcd));
        } catch (Exception e) {
            return i;
        }
    }

    public static byte[] int2Bcd(int intValue) {
        if (intValue < 0) {
            return null;
        }
        String str = String.format("%d", new Object[]{Integer.valueOf(intValue)});
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }
        return asc2Bcd(str);
    }

    public static byte[] int2Bcd(int intValue, int bcdLen) {
        if (intValue < 0 || bcdLen <= 0) {
            return null;
        }
        try {
            byte[] ret = new byte[bcdLen];
            Arrays.fill(ret, (byte) 0);
            byte[] b = int2Bcd(intValue);
            if (b.length >= bcdLen) {
                return subBytes(b, 0, bcdLen);
            }
            System.arraycopy(b, 0, ret, bcdLen - b.length, b.length);
            return ret;
        } catch (Exception e) {
            return null;
        }
    }

    public static long hex2Long(byte[] hexValue) {
        if (hexValue == null || hexValue.length > 4) {
            return 0;
        }
        byte[] hex = new byte[4];
        if (hexValue.length < 4) {
            Arrays.fill(hex, (byte) 0);
            System.arraycopy(hexValue, 0, hex, 4 - hexValue.length, hexValue.length);
        } else {
            System.arraycopy(hexValue, 0, hex, 0, 4);
        }
        long result = 0;
        for (int i = 0; i < hex.length; i++) {
            int tmpVal = hex[i] << ((3 - i) * 8);
            switch (i) {
                case 0:
                    tmpVal &= ViewCompat.MEASURED_STATE_MASK;
                    break;
                case 1:
                    tmpVal &= 16711680;
                    break;
                case 2:
                    tmpVal &= MotionEventCompat.ACTION_POINTER_INDEX_MASK;
                    break;
                case 3:
                    tmpVal &= 255;
                    break;
            }
            result += (long) tmpVal;
        }
        return result;
    }

    public static byte[] long2Hex(long longValue) {
        long l = longValue;
        long l2 = l % 16777216;
        long l3 = l2 % 65536;
        return new byte[]{(byte) ((int) (l / 16777216)), (byte) ((int) (l2 / 65536)), (byte) ((int) (l3 / 256)), (byte) ((int) (l3 % 256))};
    }

    public static String long2String(long value, int maxStringLength) {
        String formatSpace = "";
        for (int i = 0; i < maxStringLength; i++) {
            formatSpace = formatSpace + "0";
        }
        String str = "" + value;
        return maxStringLength < str.length() ? str : formatSpace.substring(str.length()) + str;
    }

    public static byte[] bit2HexByte(String bitString, int bitNum) {
        int bNum = bitNum / 8;
        if (bitNum % 8 != 0 || bNum <= 0) {
            return null;
        }
        try {
            byte[] hexRet = new byte[bNum];
            for (int k = 0; k < bNum; k++) {
                String str = bitString.substring(k * 8, (k * 8) + 8);
                byte result = 0;
                int i = str.length() - 1;
                int j = 0;
                while (i >= 0) {
                    result = (byte) ((int) (((double) result) + (((double) Byte.parseByte(str.charAt(i) + "")) * Math.pow(2.0d, (double) j))));
                    i--;
                    j++;
                }
                hexRet[k] = result;
            }
            return hexRet;
        } catch (Exception e) {
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0018, code lost:
        if (r4.length < (r5 + r6)) goto L_0x001a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] subBytes(byte[] r4, int r5, int r6) {
        /*
            r1 = 0
            if (r4 == 0) goto L_0x0011
            int r2 = r4.length
            if (r2 <= 0) goto L_0x0011
            if (r5 < 0) goto L_0x0011
            if (r6 <= 0) goto L_0x0011
            int r2 = r4.length
            if (r5 > r2) goto L_0x0011
            int r2 = r4.length
            int r2 = r2 - r5
            if (r6 <= r2) goto L_0x0013
        L_0x0011:
            r0 = r1
        L_0x0012:
            return r0
        L_0x0013:
            if (r6 < 0) goto L_0x001a
            int r2 = r4.length     // Catch:{ Exception -> 0x0024 }
            int r3 = r5 + r6
            if (r2 >= r3) goto L_0x001d
        L_0x001a:
            int r2 = r4.length     // Catch:{ Exception -> 0x0024 }
            int r6 = r2 - r5
        L_0x001d:
            byte[] r0 = new byte[r6]     // Catch:{ Exception -> 0x0024 }
            r2 = 0
            java.lang.System.arraycopy(r4, r5, r0, r2, r6)     // Catch:{ Exception -> 0x0024 }
            goto L_0x0012
        L_0x0024:
            r2 = move-exception
            r0 = r1
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: com.laikey.jatools.Utils.subBytes(byte[], int, int):byte[]");
    }

    public static byte[] gbk2Byte(String str) {
        byte[] bArr = null;
        if (str == null || str.length() <= 0) {
            return bArr;
        }
        try {
            return str.getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            return bArr;
        }
    }

    public static String byte2GBK(byte[] strData) {
        if (strData == null || strData.length <= 0) {
            return null;
        }
        try {
            return new String(strData, "gbk");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String trimChar(String orgString, char trim) {
        if (orgString == null || orgString.length() <= 0) {
            return null;
        }
        try {
            if (!orgString.endsWith(String.format("%c", new Object[]{Character.valueOf(trim)}))) {
                return orgString;
            }
            int i = orgString.length();
            do {
                i--;
                if (i <= 0) {
                    return orgString.substring(0, orgString.length() - 1);
                }
            } while (orgString.charAt(i) == trim);
            return orgString.substring(0, i + 1);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressLint("WrongConstant")
    public static String getSystemDatetime() {
        try {
            Calendar c = Calendar.getInstance();
            return String.format("%04d%02d%02d%02d%02d%02d", new Object[]{Integer.valueOf(c.get(1)), Integer.valueOf(c.get(2) + 1), Integer.valueOf(c.get(5)), Integer.valueOf(c.get(11)), Integer.valueOf(c.get(12)), Integer.valueOf(c.get(13))});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSystemDate() {
        String dt = getSystemDatetime();
        if (dt == null || dt.length() <= 8) {
            return null;
        }
        return dt.substring(0, 8);
    }

    public static String getSystemTime() {
        String dt = getSystemDatetime();
        if (dt == null || dt.length() <= 14) {
            return null;
        }
        return dt.substring(8, 14);
    }

    public static String getFormattedDateTime(String dataTime, String oldFormat, String newFormat) {
        try {
            return new SimpleDateFormat(newFormat).format(new SimpleDateFormat(oldFormat).parse(dataTime));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String addPadding(String src, boolean isLeft, char padding, int fixLen) {
        if (src.length() >= fixLen) {
            return src;
        }
        StringBuilder b = new StringBuilder();
        int padLen = fixLen - src.length();
        for (int i = 0; i < padLen; i++) {
            b.append(padding);
        }
        if (isLeft) {
            b.append(src);
        } else {
            b.insert(0, src);
        }
        return b.toString();
    }

    public static String getHash(String sha1Data) {
        try {
            return byte2Str(MessageDigest.getInstance("SHA-1").digest(hexStr2Bytes(sha1Data)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getReadableAmount(String amount) {
        if (amount == null || amount.isEmpty()) {
            return "0.00";
        }
        for (int i = 0; i < amount.length(); i++) {
            char c = amount.charAt(i);
            if (c < '0' || c > '9') {
                return "0.00";
            }
        }
        return new DecimalFormat("0.00").format(Double.parseDouble(amount) / 100.0d);
    }

    public static String callCmd(String cmd, String filter) {
        String line;
        String result = "";
        String str = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));
            do {
                line = br.readLine();
                if (line == null) {
                    break;
                }
            } while (!line.contains(filter));
            return line;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    public static byte[] findTag(String tag, byte[] buffer, int bufferLength) {
        if (tag == null || buffer == null || bufferLength <= 0) {
            return null;
        }
        byte[] findTag = asc2Bcd(tag);
        int bytesRead = 0;
        do {
            byte[] ptr = subBytes(buffer, bytesRead, buffer.length - bytesRead);
            if (ptr == null) {
                return null;
            }
            bytesRead += findTag_getNextTLVObject(ptr);
            if (Arrays.equals(findTag, findedTag)) {
                if (findTagValue == null) {
                    return null;
                }
                if (findTagValue.length <= findTagLength) {
                    return findTagValue;
                }
                return subBytes(findTagValue, 0, findTagLength);
            }
        } while (bytesRead < bufferLength);
        return null;
    }

    public static String findTag(String tag, String string) {
        if (tag == null || string == null || string.length() <= 0) {
            return null;
        }
        byte[] findTag = asc2Bcd(tag);
        byte[] buffer = asc2Bcd(string);
        int length = buffer.length;
        int bytesRead = 0;
        do {
            byte[] ptr = subBytes(buffer, bytesRead, length - bytesRead);
            if (ptr == null) {
                return null;
            }
            bytesRead += findTag_getNextTLVObject(ptr);
            if (Arrays.equals(findTag, findedTag)) {
                return bcd2Asc(findTagValue, findTagLength);
            }
        } while (bytesRead < length);
        return null;
    }

    private static int findTag_getNextTLVObject(byte[] buffer) {
        int numTagBytes;
        byte[] ptr = subBytes(buffer, 0, buffer.length);
        if ((ptr[0] & 31) == 31) {
            findedTag = new byte[2];
            findedTag = subBytes(ptr, 0, 2);
            numTagBytes = 2;
        } else {
            findedTag = new byte[1];
            findedTag = subBytes(ptr, 0, 1);
            numTagBytes = 1;
        }
        byte dataLength = ptr[numTagBytes];
        findTagLength = dataLength;
        findTagValue = subBytes(ptr, numTagBytes + 1, dataLength);
        return numTagBytes + 1 + dataLength;
    }
}
