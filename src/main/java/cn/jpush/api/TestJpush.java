package cn.jpush.api;

import java.util.List;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class TestJpush {
	public static String MASTER_SECRET = "40e35ba3ec2b2b8445f316c1";
	public static String APP_KEY = "2bb9015da508f346cab38b53";
	public static void main(String[] args) {
		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
		try {
			PushResult result = jpushClient.sendPush(buildPushObject_all_alias_alert());
			Thread.sleep(5000);
			// 请求结束后，调用 NettyHttpClient 中的 close 方法，否则进程不会退出。
			jpushClient.close();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (APIConnectionException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (APIRequestException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert("Content"))
                .build();
    }
}
