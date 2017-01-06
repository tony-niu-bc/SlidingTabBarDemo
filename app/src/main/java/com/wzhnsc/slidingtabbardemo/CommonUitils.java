package com.wzhnsc.slidingtabbardemo;

import android.text.TextUtils;

/**
 * Created by max140 on 2017/1/6.
 */

public class CommonUitils {
    // 转化标签显示风格(超过指定个数字符截断且加三点(...))
    public static String formatTabName(String tabName, int limitSize) {
        if (TextUtils.isEmpty(tabName)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        if (limitSize < tabName.length()) {
            stringBuilder.append(tabName.substring(0, limitSize - 1)).append("...");
        } else {
            stringBuilder.append(tabName);
        }

        return stringBuilder.toString();
    }
}
