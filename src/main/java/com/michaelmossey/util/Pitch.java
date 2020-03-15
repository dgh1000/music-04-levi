package com.michaelmossey.util;

import java.util.regex.*;

public class Pitch {
    public static int fromString(String s) {
        Pattern p = Pattern.compile("(\\w)(#|b)?(\\d)");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            int[] ps = { 9, 11, 0, 2, 4, 5, 7 };
            int scaleDegree = m.group(1).charAt(0) - 'A';
            if (scaleDegree < 0 || scaleDegree > 6) {
                System.out.println("Error in Pitch.fromString; illegal string: " + s);
            }
            int scaleDegreePitch = ps[scaleDegree];
            int accidental = 0;
            if (m.group(2) != null) {
                if (m.group(2).charAt(0) == '#')
                    accidental = 1;
                else if (m.group(2).charAt(0) == 'b')
                    accidental = -1;
                else {
                    System.out.println("Error in Pitch.fromString; illegal string: " + s);
                    return 0;
                }
            }
            int octaveNum = Integer.valueOf(m.group(3));
            return 12 * (1 + octaveNum) + scaleDegreePitch + accidental;
        } else {
            System.out.println("Error in Pitch.fromString; illegal string: " + s);
            return 0;
        }
    }

}