package com.example.wanandroid.viewmodel;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class enumTest {
    @IntDef({Key.red,Key.blue})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.FIELD,ElementType.PARAMETER})
    public @interface Key{
        int red=0;
        int blue=2;
    }
    
}
