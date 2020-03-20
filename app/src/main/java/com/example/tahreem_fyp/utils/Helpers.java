package com.example.tahreem_fyp.utils;

public class Helpers {

    public static int getIndexOfHighestNumber(int a[]) {
        int max = a[0];
        int index = 0;

        for (int i = 0; i < a.length; i++)
        {
            if (max < a[i])
            {
                max = a[i];
                index = i;
            }
        }

        return index;
    }

}
