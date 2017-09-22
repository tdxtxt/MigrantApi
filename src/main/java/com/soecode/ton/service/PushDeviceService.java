package com.soecode.ton.service;

import java.util.List;

public interface PushDeviceService {
	public void saveToken(String userId,String mobile, String pushToken);
	public void deleteToken(String pushToken);
	public List<String> getAllToken();
	public List<String> getTokenByMobile(String mobile);
}
