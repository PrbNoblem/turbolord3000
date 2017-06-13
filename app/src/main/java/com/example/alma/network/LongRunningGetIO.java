package com.example.alma.network;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class LongRunningGetIO extends AsyncTask<Void, Void, String> {
    private Activity activity;

    public LongRunningGetIO(Activity activity){
        this.activity = activity;
    }

    protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
        InputStream in = entity.getContent();

        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n>0) {
            byte[] b = new byte[4096];
            n =  in.read(b);

            if (n>0) out.append(new String(b, 0, n));
        }

        return out.toString();
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpGet httpGet = new HttpGet("http://vm39.cs.lth.se:9000/device");
        String text = null;

        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);

            HttpEntity entity = response.getEntity();

            text = getASCIIContentFromEntity(entity);

        } catch (Exception e) {
            return e.getLocalizedMessage();
        }

        return text;
    }

    @Override
    protected void onPostExecute(String results) {
        if (results!=null) {
            EditText et = (EditText) activity.findViewById(R.id.my_edit);

            et.setText(results);
        }

        Button b = (Button) activity.findViewById(R.id.my_button);

        b.setClickable(true);
    }
}
