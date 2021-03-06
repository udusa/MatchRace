package com.matchrace.matchrace.classes;

import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.matchrace.matchrace.modules.JsonReader;

/**
 * Created by vladlanda on 11/05/15.
 */
public class GetWeatherTask extends AsyncTask<String, Integer, String[]> {

    TextView tvWindSpeed, tvWindDeg;
    private String[] wind;
    public GetWeatherTask(TextView _tvWindSpeed, TextView _tvWindDeg) {
        tvWindDeg = _tvWindDeg;
        tvWindSpeed = _tvWindSpeed;
        wind = new String[2];
        wind[0]="";
        wind[1]="";
    }

    @Override
    protected String[] doInBackground(String... urls) {


        try {
            //JSONObject jsonWeather = readJsonFromUrl(urls[0]);
            JSONObject jsonWeather = JsonReader.readJsonFromUrl(urls[0]);
            JSONObject jsonWind = jsonWeather.getJSONObject("wind");
            String windSpeed = jsonWind.getString("speed");
            String windDeg = jsonWind.getString("deg");
            wind[0] = windSpeed;
            wind[1] = windDeg;

            return wind;
        } catch (JSONException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] wind) {
        if (wind == null) return;
        double speed = Double.parseDouble(wind[0]);
        tvWindSpeed.setText(""+speed+" m/s");
        tvWindDeg.setText(wind[1]);

    }

    private JSONObject readJsonFromUrl(String url) throws IOException {
        try {
            String jsonText = "";

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            InputStream is = httpEntity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            reader.close();
            jsonText = sb.toString();

            JSONObject jsonWeather = new JSONObject(jsonText);

            return jsonWeather;

        } catch (IOException ie) {
            throw new IOException();
        } catch (JSONException je) {
            return null;
        }
    }

    public String getWindDeg(){return wind[1];}
    public String getWindSpeed(){return wind[0];}
}
