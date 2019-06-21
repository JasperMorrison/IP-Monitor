package com.zzy.net;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NetApp extends Application
{
	public static NetApp mInstance;
	private ArrayList<AppInfo> listAPP = new ArrayList<>();

	@Override
	public void onCreate()
	{
		super.onCreate();
		mInstance = this;
		HanziToPinyin.init();
	}

	public ArrayList<AppInfo> getAppList() {
		return listAPP;
	}

	public void setAppList(ArrayList<AppInfo> lsApp) {
		for (AppInfo app: lsApp){
			if (!isInList(listAPP, app)){
				listAPP.add(app);
			}
		}
	}

	private boolean isInList(List<AppInfo> l, AppInfo a){
		for (AppInfo i: l){
			if (i.getUid() == a.getUid()){
				return true;
			}
		}
		return false;
	}
}
