package com.soecode.ton.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soecode.ton.dao.CityDao;
import com.soecode.ton.dto.city.ReCity;
import com.soecode.ton.entity.City;
import com.soecode.ton.service.CityService;

@Service
public class CityServiceImpl implements CityService {
	@Autowired
	CityDao cityDao;

	@Override
	public List<ReCity> getAllCity() {
		List<City> citys = cityDao.queryAll();//总共的数据
		List<ReCity> reCitys = new ArrayList<ReCity>();
		for (City city : citys) {
			if ("0".equals(city.getParentId())) {// 一级城市
				ReCity reCity = new ReCity();
				reCity.setId(city.getId());
				reCity.setName(city.getName());
				reCity.setParentId("0");
				List<ReCity> subCitys = new ArrayList<ReCity>();
				for (City subCity : citys) {// 二级城市
					if (subCity.getParentId().equals(city.getId())) {
						ReCity subReCity = new ReCity();
						subReCity.setId(subCity.getId());
						subReCity.setName(subCity.getName());
						subReCity.setParentId(subCity.getParentId());
						subCitys.add(subReCity);
					}
				}
				reCity.setSubCitys(subCitys);
				if("重庆市".equals(reCity.getName())){
					reCitys.add(0, reCity);
				}else{
					reCitys.add(reCity);
				}
			}
		}
		return reCitys;
	}

}
