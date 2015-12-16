package com.hyogij.jsonclient.StringUtils;

/**
 * Created by hyogij on 15. 12. 16..
 */
public class Utils {
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
