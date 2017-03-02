package bbhs.appbowl2017;

import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by ratra on 3/1/2017.
 */
public class MemoryTile  {
    public ImageView imageView;
    public Uri image;
    boolean flipped = false;
    int num;
    public  MemoryTile(ImageView imageView, Uri image, int num){
        this.imageView = imageView;
        this.image =image;
        this.num = num;
    }

    public void flip(){
    if(flipped){
        imageView.setImageURI(image);
    }
        else{
        imageView.setImageURI(null);
    }
        flipped = !flipped;


    }

}
