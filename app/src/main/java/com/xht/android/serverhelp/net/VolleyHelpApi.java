package com.xht.android.serverhelp.net;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.xht.android.serverhelp.App;
import com.xht.android.serverhelp.util.LogHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedHashMap;

public class VolleyHelpApi extends BaseApi{
	private static final String TAG = "VolleyHelpApi";
	
	private static VolleyHelpApi sVolleyHelpApi;
	
	public static synchronized VolleyHelpApi getInstance() {
		if (sVolleyHelpApi == null) {
			sVolleyHelpApi = new VolleyHelpApi();
		}
		return sVolleyHelpApi;
	}
	
	private VolleyHelpApi() {}

	/**
	 * 获取办证的列表的数据
	 * @param uId
	 * @param apiListener
     */
	public void getBZItems(final int uId, final APIListener apiListener) {
		String urlS = MakeURL(BZ_ITEMS_URL, new LinkedHashMap<String, Object>() {
			{put("userid", uId);}
		});
		LogHelper.i(TAG, urlS);
		JsonObjectRequest req = new JsonObjectRequest(urlS, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					JSONArray jsonArray = response.optJSONArray("entity");

					apiListener.onResult(jsonArray);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("获取办证中列表数据出错");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 获取办证进度标签下的数据
	 * @param ordId
	 * @param apiListener
	 */
	public void getBZProcs(final int ordId, final APIListener apiListener) {
		String urlS = MakeURL(BZ_ProsInit_URL, new LinkedHashMap<String, Object>() {
			{put("orderid", ordId);}
		});
		LogHelper.i(TAG, urlS);
		JsonObjectRequest req = new JsonObjectRequest(urlS, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {
					JSONArray jsonArray = response.optJSONArray("entity");

					apiListener.onResult(jsonArray);
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("获取办证进度标签下的数据出错");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}
	
	private boolean isResponseError(JSONObject jb){
		String errorCode = jb.optString("code","0");

		if(errorCode.equals("1")){
			return false;
		}		
		return true;
	}
	
	public static String MakeURL(String p_url, LinkedHashMap<String, Object> params) {
		StringBuilder url = new StringBuilder(p_url);
		if(url.indexOf("?")<0)
			url.append('?');

		for(String name : params.keySet()){
			url.append('&');
			url.append(name);
			url.append('=');
			url.append(String.valueOf(params.get(name)));
		}
		String temStr = url.toString().replace("?&", "?");
		
		return temStr;
	}
	
	/**
	 * 首页访问数据
	 * @param apiListener
     */
	public void getMainData( final APIListener apiListener) {
		String urlString = MakeURL(URL_Main, new LinkedHashMap<String, Object>() {{
			put("userid", 2);

		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					LogHelper.i(TAG, "----首页的所有信息--" + response.toString());
					apiListener.onResult(response);

				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 *  获取任务池中的数据
	 * @param apiListener
     */
	public void getTaskBarData( final APIListener apiListener) {
		String urlString = MakeURL(URL_TaskBar, new LinkedHashMap<String, Object>() {{
			//put("ordContactId", uid);

		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 接受任务
	 */
	public void postTaskAccept(int userId, JSONObject jsonObject, final APIListener apiListener) {
		JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, Task_ITEM_POST_URL, jsonObject, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					apiListener.onResult(response);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}


	/**
	 * 访问个人绩效数据
	 * @param apiListener
	 */
	public void getPersonalData(final int uid, final APIListener apiListener) {
		String urlString = MakeURL(PERSONAL_POST_URL, new LinkedHashMap<String, Object>() {{
			put("userid", uid);

		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);

				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

	/**
	 * 访问预警数据
	 * @param apiListener
	 */
	public void getWarningData(final int uid, final APIListener apiListener) {
		String urlString = MakeURL(WARNING_POST_URL, new LinkedHashMap<String, Object>() {{
			put("userid", uid);

		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);

				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}



	/**
	 * 访问预警数据
	 * @param apiListener
	 */
	public void getTXLData( final APIListener apiListener) {
		String urlString = MakeURL(CONTACTSPOST_URL, new LinkedHashMap<String, Object>() {{
			//put("userid", uid);

		}});
		JsonObjectRequest req = new JsonObjectRequest(urlString, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				LogHelper.i(TAG, response.toString());
				if (isResponseError(response)) {
					String errMsg = response.optString("message");
					apiListener.onError(errMsg);
				} else {

					LogHelper.i(TAG, "----的所有信息--" + response.toString());
					apiListener.onResult(response);

				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				int type = VolleyErrorHelper.getErrType(error);
				switch (type) {
					case 1:
						LogHelper.i(TAG, "超时");
						break;
					case 2:
						LogHelper.i(TAG, "服务器问题");
						break;
					case 3:
						LogHelper.i(TAG, "网络问题");
						break;
					default:
						LogHelper.i(TAG, "未知错误");
				}
				apiListener.onError("服务器繁忙，稍后再试...");
			}
		});
		App.getInstance().addToRequestQueue(req, TAG);
	}

}
