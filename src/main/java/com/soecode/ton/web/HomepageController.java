package com.soecode.ton.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomepageController {
	
	@RequestMapping("")
	public String homepage(){
		return "common/uploadFile";
	}
	
}
