package com.zzx.service;

import com.alibaba.fastjson.JSONObject;
import com.zzx.bean.Cache;
import com.zzx.bean.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 公号模版发送者
 * 
 * @author kane
 *
 */
@Slf4j
public class PublicModelWechatSender  {



	static int COUNT=0;
	int zzx=0;
	private final static String SENDURL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public void send(int key, Map<String, Object> msg, String token) {

		if(System.getProperty("state").equals("stop")){
			return;
		}
		
		String json = (String)msg.get("json");
		
		try {
			String ret = getJson(token, json);

			JSONObject retJsonObj = JSONObject.parseObject(ret);
			Cache.cacheList.remove(key);
			System.out.println(key+"线程："+zzx);
			zzx++;
			boolean result= retJsonObj.containsKey("errcode") && retJsonObj.getString("errcode").equals("0");

			if (result) {
				//TODO 日志添加
				LogEntity logEntity=new LogEntity();
				logEntity.setOpenID(String.valueOf(msg.get("uid")));
				logEntity.setResult("成功");
				logEntity.setDate(simpleDateFormat.format(new Date()));
				Cache.list.add(logEntity);
			}else {
				//TODO 日志添加
				LogEntity logEntity=new LogEntity();
				logEntity.setOpenID(String.valueOf(msg.get("uid")));
				logEntity.setResult("失败");
				logEntity.setDate(simpleDateFormat.format(new Date()));
				Cache.list.add(logEntity);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "系统出错程序停止！");
			System.setProperty("state","stop");
		}
	}

	private String getJson(String token, String json) throws Exception {
		String url = SENDURL + token;
		return httpPostWithJSON(url, json);
	}

	/**
	 * http提交一个json请求
	 * @param url
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static String httpPostWithJSON(String url, String json) throws Exception {
		HttpPost httpPost = null;
		CloseableHttpClient client = null ;
		String respContent = null;
		try{
			httpPost = new HttpPost(url);
			client = HttpClients.createDefault();

			StringEntity entity = new StringEntity(json, "utf-8");// 解决中文乱码问题
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			System.out.println();

			HttpResponse resp = client.execute(httpPost);
			if (resp.getStatusLine().getStatusCode() == 200) {
				HttpEntity he = resp.getEntity();
				respContent = EntityUtils.toString(he, "UTF-8");
			}
		}finally {
			if(null != httpPost){
				httpPost.releaseConnection();
			}
			if(null != client){
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return respContent;
	}


}
