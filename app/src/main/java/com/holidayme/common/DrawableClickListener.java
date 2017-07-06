package com.holidayme.common;

/**
 * Created by devrath.rathee on 01-02-2017.
 */

public interface DrawableClickListener {

        enum DrawablePosition { TOP,BOTTOM,LEFT,RIGHT }
        void onClick(DrawablePosition target);

}
