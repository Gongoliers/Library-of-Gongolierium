package com.thegongoliers.util;

public abstract class AsyncTask<T, S> {
	public void execute(T param){
		new Thread(()->{
			onPostExecute(doInBackground(param));
		}).start();
	}
	
	public abstract S doInBackground(T param);
	
	public abstract void onPostExecute(S result);
}
