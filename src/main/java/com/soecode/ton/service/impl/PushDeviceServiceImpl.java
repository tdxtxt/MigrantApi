package com.soecode.ton.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.dao.PushDeviceDao;
import com.soecode.ton.help.TextUtils;
import com.soecode.ton.service.PushDeviceService;
@Service
public class PushDeviceServiceImpl implements PushDeviceService {
	@Autowired
	PushDeviceDao pushDao;
	@Override
	public void saveToken(String userId,String mobile, String pushToken) {
		if(TextUtils.isEmpty(pushToken)) return;
		
		int countToken = pushDao.countToken(pushToken);
		if(countToken > 0){
			deleteToken(pushToken);
		}
		
		int countUserId = pushDao.countUserId(userId);
		if(countUserId > 0){//该userid已经注册到其他设备上了
			pushDao.update(userId,mobile,pushToken);
		}else{//该设备没有注册，需要新增
			pushDao.insert(userId, mobile, pushToken);
		}
	}

	@Override
	public void deleteToken(String pushToken) {
		pushDao.deleteByToken(pushToken);
	}

	@Override
	public List<String> getAllToken() {
		return pushDao.queryAllToken();
	}

	@Override
	public List<String> getTokenByMobile(String mobile) {
		return pushDao.queryTokensByMobile(mobile);
	}

}
