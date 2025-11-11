package com.rajdeep.util;

import com.rajdeep.model.BaseCharacter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 Simple utility: sort characters by speed descending (who acts first)
*/
public class SortUtils {
    public static void sortBySpeedDesc(List<? extends BaseCharacter> list) {
        Collections.sort(list, Comparator.comparingInt(BaseCharacter::getSpeed).reversed());
    }
}
