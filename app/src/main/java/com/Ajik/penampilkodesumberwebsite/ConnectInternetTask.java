package com.Ajik.penampilkodesumberwebsite;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class ConnectInternetTask extends AsyncTask<String, Void, String> {

    private Context mContext;

    public ConnectInternetTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String s1 = strings[0];
        InputStream inputStream;
        try {
            URL url = new URL(s1);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("GET");
            conn.connect();
            inputStream = conn.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }

            bufferedReader.close();
            inputStream.close();

            return stringBuffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("st", String.valueOf(s.length()));
        if (s.length() > 0) {
            /*Jika mendapat source code*/
            MainActivity.textResult.setText(s);
        } else {
            MainActivity.textResult.setText("Tidak ada hasil");
        }
    }
}
