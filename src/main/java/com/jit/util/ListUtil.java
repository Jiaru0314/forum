package com.jit.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: forum
 * @description:
 * @author: XZQ
 * @create: 2019-12-08 20:51
 **/
public class ListUtil {

    public static List<Integer> stringToList(String ids) {
        if (ids == null || "".equals(ids)) {
            return null;
        }
        String[] split = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String str : split) {
            if (str.length() != 0) {
                int id = Integer.parseInt(str);
                if (!list.contains(id)) {
                    list.add(id);
                }
            }
        }
        return list;
    }
}
