package se.leap.bitmaskclient.test;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class FromAssets {

    Context context;

    public FromAssets(Context context) {
        this.context = context;
    }
    public String toString(String filename) throws IOException, JSONException {
        String result = "";
        InputStream is = context.getAssets().open(filename);
        byte[] bytes = new byte[is.available()];
        if(is.read(bytes) > 0) {
            result = new String(bytes);
        }
        return result;
    }
}
