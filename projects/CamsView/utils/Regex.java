/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import com.google.re2j.Matcher;
import com.google.re2j.Pattern;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author djoker
 */
public class Regex {
    private Matcher a;

    public Regex(CharSequence charSequence, Pattern pattern) {
        if (charSequence != null && pattern != null) {
            this.a = pattern.matcher(charSequence);
        }
    }

    public Regex(Object obj, String str) {
        this(obj.toString(), str);
    }

    public Regex(Object obj, String str, int i) {
        this(obj.toString(), str, i);
    }

    public Regex(Object obj, Pattern pattern) {
        this(obj.toString(), pattern);
    }

    public Regex(String str, String str2) {
        if (str != null && str2 != null) {
            this.a = Pattern.compile(str2, 34).matcher(str);
        }
    }

    public Regex(String str, String str2, int i) {
        if (str != null && str2 != null) {
            this.a = Pattern.compile(str2, i).matcher(str);
        }
    }

    public Regex(String str, Pattern pattern) {
        if (str != null && pattern != null) {
            this.a = pattern.matcher(str);
        }
    }

    public Regex(Matcher matcher) {
        if (matcher != null) {
            this.a = matcher;
        }
    }

    public static String escape(String str) {
        return Pattern.quote(str);
    }

    public static String[] getLines(String str) {
        int i = 0;
        if (str == null) {
            return new String[0];
        }
        String[] split = str.split("[\r\n]{1,2}");
        int length = split.length;
        String[] strArr = new String[length];
        while (i < length) {
            strArr[i] = split[i].trim();
            i++;
        }
        return strArr;
    }

    public static boolean matches(Object obj, String str) {
        return new Regex(obj, str).matches();
    }

    public static boolean matches(Object obj, Pattern pattern) {
        return new Regex(obj, pattern).matches();
    }

    public static String replace(String str, String str2, String str3) {
        return Pattern.compile(str2, 40).matcher(str).replaceAll(str3);
    }

    public int count() {
        int i = 0;
        if (this.a != null) {
            this.a.reset();
            while (this.a.find()) {
                i++;
            }
        }
        return i;
    }

    public String[] getColumn(int i) {
        if (this.a == null) {
            return null;
        }
        int i2 = i + 1;
        Matcher matcher = this.a;
        matcher.reset();
        List arrayList = new ArrayList();
        while (matcher.find()) {
            arrayList.add(matcher.group(i2));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public String getMatch(int i) {
        if (this.a != null) {
            Matcher matcher = this.a;
            matcher.reset();
            if (matcher.find()) {
                return matcher.group(i + 1);
            }
        }
        return null;
    }

    public String getMatch(int i, int i2) {
        if (this.a != null) {
            Matcher matcher = this.a;
            matcher.reset();
            int i3 = i + 1;
            int i4 = 0;
            while (matcher.find()) {
                if (i4 == i2) {
                    return matcher.group(i3);
                }
                i4++;
            }
        }
        return null;
    }

    public Matcher getMatcher() {
        if (this.a != null) {
            this.a.reset();
        }
        return this.a;
    }

    public String[][] getMatches() {
        if (this.a == null) {
            return (String[][]) null;
        }
        Matcher matcher = this.a;
        matcher.reset();
        List arrayList = new ArrayList();
        while (matcher.find()) {
            String[] strArr;
            int groupCount = matcher.groupCount();
            int i = 1;
            if (groupCount == 0) {
                strArr = new String[(groupCount + 1)];
                i = 0;
            } else {
                strArr = new String[groupCount];
            }
            for (int i2 = i; i2 <= groupCount; i2++) {
                strArr[i2 - i] = matcher.group(i2);
            }
            arrayList.add(strArr);
        }
        return arrayList.size() == 0 ? new String[0][] : (String[][]) arrayList.toArray(new String[0][]);
    }

    public String[] getRow(int i) {
        if (this.a != null) {
            Matcher matcher = this.a;
            matcher.reset();
            int i2 = 0;
            while (matcher.find()) {
                if (i2 == i) {
                    int groupCount = matcher.groupCount();
                    String[] strArr = new String[groupCount];
                    for (int i3 = 1; i3 <= groupCount; i3++) {
                        strArr[i3 - 1] = matcher.group(i3);
                    }
                    return strArr;
                }
                i2++;
            }
        }
        return null;
    }

    public boolean matches() {
        Matcher matcher = this.a;
        if (matcher == null) {
            return false;
        }
        matcher.reset();
        return matcher.find();
    }

    public void setMatcher(Matcher matcher) {
        this.a = matcher;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String[][] matches = getMatches();
        int length = matches.length;
        for (int i = 0; i < length; i++) {
            String[] strArr = matches[i];
            int length2 = strArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                stringBuilder.append("match[");
                stringBuilder.append(i);
                stringBuilder.append("][");
                stringBuilder.append(i2);
                stringBuilder.append("] = ");
                stringBuilder.append(strArr[i2]);
                stringBuilder.append(System.getProperty("line.separator"));
            }
        }
        this.a.reset();
        return stringBuilder.toString();
    }
}