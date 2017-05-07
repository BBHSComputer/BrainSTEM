package bbhs.appbowl2017;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by adamf on 5/7/2017.
 */

public class StrokeDrawable extends GradientDrawable {
    public StrokeDrawable(){
        super();
    }
    public void setStroke(int arg){
        super.setStroke(7, arg);
    }

}
