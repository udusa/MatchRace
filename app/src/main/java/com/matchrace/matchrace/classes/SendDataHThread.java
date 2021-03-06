package com.matchrace.matchrace.classes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.HandlerThread;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * HandlerThread for sending the data to DB.
 * 
 */
public class SendDataHThread extends HandlerThread {

	private HttpURLConnection urlConnection;
	private String lat, lng, speed, bearing, event;
	private String name,fullUserName;
    //ADDED
    private String user,pass;
    private boolean login,registerSucced=false;
    private String phpSetBuoys;

	public SendDataHThread(String name) {
		super(name);
		this.name = name;
	}

	@Override
	public void run() {
		httpConnSendData();
	}

	/**
	 * Creates the HTTP connection for sending data to DB.
	 */
	private void httpConnSendData() {
		try {
			//URL url = new URL(C.URL_INSERT_CLIENT + "&Latitude=" + lat +"&Longitude=" + lng +"&Speed="+ speed + "&Azimuth="+ bearing + "&Bearing=" + bearing + "&Information=" + fullUserName + "&Event=" + event);
            URL url = null;
            switch (name) {
                case "CreateNewUser": {
                    url = new URL(C.URL_REGISTER_CLIENT + "&User=" + user + "&Pass=" + pass + "&Login=" + login + "&Latitude=" + lat + "&Longitude=" + lng + "&Speed=" + speed + "&Azimuth=" + bearing + "&Bearing=" + bearing + "&Event=" + event);
                    break;
                }
                case "SendBuoys": {
                    url = new URL(phpSetBuoys);
                    break;
                }
                case "UpdateClient": {
                    url = new URL(C.URL_UPDATE_CLIENT + "&User=" + user + "&Latitude=" + lat + "&Longitude=" + lng + "&Speed=" + speed + "&Azimuth=" + bearing + "&Bearing=" + bearing + "&Event=" + event);
                    break;
                }
            }
            urlConnection = (HttpURLConnection) url.openConnection();

			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String result = br.readLine();
				if (!result.startsWith("OK")) { // Something is wrong.
					Log.i(name, "Not OK!");
				}
				else { // Data sent.
					Log.i(name, "OK!");
                    registerSucced=true;
				}
			}
			catch (IOException e) {
				Log.i(name, "IOException");
				urlConnection.disconnect();
			}
			urlConnection.disconnect();
		}
		catch (MalformedURLException e) {
			Log.i(name, "MalformedURLException");
		}
		catch (IOException e) {
			Log.i(name, "IOException");
		}
	}

	// Getters and Setters.
	public String getFullUserName() {
		return fullUserName;
	}

	public void setFullUserName(String fullUserName) {
		this.fullUserName = fullUserName;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getBearing() {
		return bearing;
	}

	public void setBearing(String bearing) {
		this.bearing = bearing;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

    //ADDED

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() { return pass; }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getLogin() { return login; }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public boolean getRegisterSucess(){return registerSucced;}

    public void createEvent(BuoyPosition[] buoysArr,TimePicker tp,DatePicker dp,String event){
        String time = tp.getCurrentHour()+":"+tp.getCurrentMinute();
        String date = dp.getYear()+"-"+(dp.getMonth()+1)+"-"+dp.getDayOfMonth();
        phpSetBuoys = ""+C.URL_SET_BUOYS+"&Event="+event+"&sTime="+time+"&Date="+date;
        for (int i=0;i<buoysArr.length;i++){
            if(buoysArr[i]!=null) {
                phpSetBuoys += "&b" + (i + 1) + "lat=" + buoysArr[i].getLat();
                phpSetBuoys += "&b" + (i + 1) + "lng=" + buoysArr[i].getLng();
            }else{
                phpSetBuoys += "&b" + (i + 1) + "lat=";
                phpSetBuoys += "&b" + (i + 1) + "lng=";
            }
        }

       
    }

}
