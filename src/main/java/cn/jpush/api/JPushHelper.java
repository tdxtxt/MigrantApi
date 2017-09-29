package cn.jpush.api;

import java.util.List;

import com.soecode.ton.help.Logs;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushHelper {
	public String MASTER_SECRET = "40e35ba3ec2b2b8445f316c1";
	public String APP_KEY = "2bb9015da508f346cab38b53";
	public JPushClient jpushClient;
	
	 /**
     * 单例
     */
    public static JPushHelper getInstance() {
        return InstanceHolder.instance;
    }
    static class InstanceHolder {
        final static JPushHelper instance = new JPushHelper();
    }
	private JPushHelper() {}
	public void sendTaskToClientUser(List<String> tagValues,String taskDesc,String taskId) throws Exception{
		if(jpushClient == null) jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
		if(!(tagValues != null && tagValues.size() > 0)) return;
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					PushResult result = jpushClient.sendPush(buildPayloadAllClient(tagValues, taskDesc,taskId));
					Thread.sleep(5000);
					// 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
					jpushClient.close();
				} catch (InterruptedException e) {
					Logs.error(e.getMessage());
					System.out.println(e.getMessage());
					e.printStackTrace();
				} catch (APIConnectionException e) {
					Logs.error(e.getMessage());
					System.out.println(e.getMessage());
					e.printStackTrace();
				} catch (APIRequestException e) {
					Logs.error(e.getMessage());
					System.out.println(e.getMessage());
					e.printStackTrace();
				}				
			}
		}).start();
		
	}
	public PushPayload buildPayloadAllClient(List<String> tagValues,String content,String taskId){
		return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(tagValues))
                .setMessage(Message.newBuilder()
                        .setMsgContent(taskId)//必须要加msg
                        .addExtra("taskId", taskId).build())
                .setNotification(Notification.alert(content))
                .build();
	}
}
