package com.vasanth.dev.aeriesorhs.helpers;

/**
 * Created by Vasanth Sadhasivan on 5/28/2016.
 */
public class StringOps {
    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        } else if (repeat <= 0) {
            return "";
        } else {
            int inputLength = str.length();
            if (repeat != 1 && inputLength != 0) {
                if (inputLength == 1 && repeat <= 8192) {
                    return repeat(str.charAt(0), repeat);
                } else {
                    int outputLength = inputLength * repeat;
                    switch (inputLength) {
                        case 1:
                            return repeat(str.charAt(0), repeat);
                        case 2:
                            char ch0 = str.charAt(0);
                            char ch1 = str.charAt(1);
                            char[] output2 = new char[outputLength];

                            for (int buf = repeat * 2 - 2; buf >= 0; --buf) {
                                output2[buf] = ch0;
                                output2[buf + 1] = ch1;
                                --buf;
                            }

                            return new String(output2);
                        default:
                            StringBuilder var9 = new StringBuilder(outputLength);

                            for (int i = 0; i < repeat; ++i) {
                                var9.append(str);
                            }

                            return var9.toString();
                    }
                }
            } else {
                return str;
            }
        }
    }

    public static String repeat(char ch, int repeat) {
        char[] buf = new char[repeat];

        for (int i = repeat - 1; i >= 0; --i) {
            buf[i] = ch;
        }

        return new String(buf);
    }
}
