
package org.firebird.monitor.util;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class MonitorUtil {

    // Constantes para o método fillStr
    public static final int FILL_LEFT = 0;
    public static final int FILL_RIGHT = 1;
    public static final int FILL_LEFT_RIGHT = 2;

    public static String fillStr(String string, String sequence, int size, int orientation) {
        StringBuilder str = new StringBuilder();
        str.append(string);
        for (int i = 0; i < size - string.length(); i++) {
            switch (orientation) {
                case 0:
                    str.insert(0, sequence);
                    break;
                case 1:
                    str.append(sequence);
                    break;
                case 2:
                    if (i % 2 == 0) {
                        str.insert(0, sequence);
                    } else {
                        str.append(sequence);
                    }
            }

        }
        return str.toString();
    }

    public static String formatStringDateTime(String mask, String date) {
        SimpleDateFormat formatTime = new SimpleDateFormat(mask);
        Date d = null;
        try {
            d = (Date) formatTime.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTime.format(d);
    }

    public static String formatDateTimeSystem(String mask) {
        Date date = new Date(System.currentTimeMillis());
        Format format = new SimpleDateFormat(mask);
        return format.format(date);
    }

    public static String formatStringDecimal(String mask, double value) {
        DecimalFormat df  = new DecimalFormat(mask);
        return df.format(value);
    }
}
