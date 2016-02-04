package com.hyogij.jsonclient.helper;

/**
 * Created by hyogij on 15. 12. 16..
 */
public class StringUtils {
    public static String getActvityTitle(String title, String header, String id) {
        StringBuilder actvityTitle = new StringBuilder(title);
        actvityTitle.append(" : ");
        actvityTitle.append(header);
        actvityTitle.append("(");
        actvityTitle.append(id);
        actvityTitle.append(")");
        return actvityTitle.toString();
    }
}
