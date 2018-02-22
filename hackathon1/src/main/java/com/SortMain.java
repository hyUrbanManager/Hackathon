package com;

import com.sort.NumMaterial;
import com.sort.SortUtils;

import org.junit.Test;

/**
 * Created by Administrator on 2018/2/18.
 *
 * @author hy 2018/2/18
 */
public class SortMain {

    @Test
    public void debug() {
        main(new String[]{"123"});
    }

    public static void main(String[] args) {

        int[] arr = NumMaterial.unSortNum();

        SortUtils.bubble(arr);

        NumMaterial.isSortCorrect(arr);
    }

}
