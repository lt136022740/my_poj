package com.szkk.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.szkk.utils.ViewUtil;
import com.szkk.www.R;

public class SZHttpRequestExecutor {
	private HttpClient httpClient = null;
	public static final int RETRIEVE_IDLE_CONNECTTION_INTERVAL = 5000;
	public static final int CONNECTION_TIMEOUT_DELAY = 10000;
	public static final int SO_TIMEOUT_DELAY = 20000;
	private IdleConnectionMonitorThread cmMonitor;
	
	
	public void addRequest(SZHttpRequest request) {
		processRequest(request);
	}

	private void processRequest(SZHttpRequest request) {
		new AsyncHttpExcutor().execute(request);
	}

	private class AsyncHttpExcutor extends AsyncTask<SZHttpRequest, Void, SZHttpRequest> {
		private SZException exception = null;
		private HttpResponse response = null;

		@Override
		protected SZHttpRequest doInBackground(SZHttpRequest... requests) {
			SZHttpRequest szHttpRequest = requests[0];
			HttpClient httpClient = getHttpClient();
			HttpUriRequest httpUriRequest = szHttpRequest.getRequest();
			
			try {
				response = httpClient.execute(httpUriRequest);
				szHttpRequest.setResponse(response);
			} catch (ConnectTimeoutException e) {
				exception = SZException.ConnectTimeoutException;
				response = null; 
				httpUriRequest.abort();
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				exception = SZException.SocketTimeoutException;   
				response = null;
				httpUriRequest.abort();
				e.printStackTrace();
			} catch (ConnectException e) {
				Log.i("tag1", "e " + e);
				exception = SZException.ConnectException;
				response = null;
				httpUriRequest.abort();
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				exception = SZException.ClientProtocolException;
				response = null;
				httpUriRequest.abort();
				e.printStackTrace();    
			} catch (IOException e) {
				exception = SZException.IOException;
				response = null;
				httpUriRequest.abort();
				e.printStackTrace();
			}finally{
				if(response != null){
					HttpEntity httpEntity = response.getEntity();
					try {  
						byte[] data = EntityUtils.toByteArray(httpEntity);
						//data = Base64.decode(data, Base64.DEFAULT);
						szHttpRequest.receiveDataBuffer = data;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					
				}
			}
			return szHttpRequest;
		}  

		@Override
		protected void onPostExecute(SZHttpRequest szHttpRequest) {
			super.onPostExecute(szHttpRequest);
			if(response != null){
				int statusCode = szHttpRequest.getResponse().getStatusLine().getStatusCode();
				if(statusCode == HttpStatus.SC_OK){
					szHttpRequest.requestOk();
				}else{
					szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.server_error), statusCode);
				}
				
				HttpEntity entity = szHttpRequest.getResponse().getEntity();
				if (entity != null) {
					try {
						entity.consumeContent();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				switch(exception){
					case ConnectTimeoutException:
						szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.connection_timetout), -1);
						break;
					case SocketTimeoutException:
						szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.socket_connection_timeout), -2);
						break;
					case ConnectException:
						szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.server_exception), -3);
						break;
					case ClientProtocolException:
						szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.protocol_exception),-4);
						break;
					case IOException:
						szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.io_exception),-5);
						break;
					case Exception:
						szHttpRequest.requestFailed(ViewUtil.shareActivity.getResources().getString(R.string.exception),-6);
						break;
				}
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}
	
	private HttpClient getHttpClient() {
		if (httpClient == null) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);

			// 增加最大连接到50
			ConnManagerParams.setMaxTotalConnections(params, 50);
			// 增加每个路由的默认最大连接到20
			ConnPerRouteBean connPerRoute = new ConnPerRouteBean(20);
			// 对localhost:80增加最大连接到50
			// HttpHost localhost = new HttpHost("locahost", 80); connPerRoute.setMaxForRoute(new HttpRoute(localhost),
			// 50);
			// connPerRoute.setMaxForRoute(new HttpRoute(localhost), 50);
			ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);
			// 设置 连接超时 时长
			params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT_DELAY);
			// 设置 返回数据超时 时长
			params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT_DELAY);
			// 设置 Cookie策略
			params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);

			// 注册要使用的协议
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			// schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

			ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

			httpClient = new DefaultHttpClient(cm, params);

			cmMonitor = new IdleConnectionMonitorThread(cm);
			cmMonitor.start();
		}
		return httpClient;
	}
	
	private class IdleConnectionMonitorThread extends Thread {
		private final ClientConnectionManager connMgr;
		private volatile boolean shutdown;

		public IdleConnectionMonitorThread(ClientConnectionManager connMgr) {
			super();
			this.connMgr = connMgr;
		}

		@Override
		public void run() { 
			try {
				while (!shutdown) {
					synchronized (this) {
						wait(RETRIEVE_IDLE_CONNECTTION_INTERVAL);
						// 关闭过期连接
						connMgr.closeExpiredConnections();
						// 关闭空闲超过30秒的连接
						//connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}
	}
}
