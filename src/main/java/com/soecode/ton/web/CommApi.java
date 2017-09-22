package com.soecode.ton.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.soecode.ton.dto.Result;
import com.soecode.ton.dto.city.ReCity;
import com.soecode.ton.entity.JobType;
import com.soecode.ton.service.CityService;
import com.soecode.ton.service.CustomerService;
import com.soecode.ton.service.JobTypeService;
/**
 * https://github.com/tdxtxt/MigrantApi.git
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comm")
public class CommApi extends BaseController{
	@Autowired
	private CityService cityService;
	@Autowired
	private JobTypeService jobTypeService;
	@Autowired 
	private CustomerService customerService;
	
	@ResponseBody
	@RequestMapping(value = "/allCity", method={RequestMethod.GET,RequestMethod.POST})
	private Result<List<ReCity>> getAllCity(){
		List<ReCity> data = cityService.getAllCity();
		if(data != null && data.size() > 0){
			return new Result<List<ReCity>>(data);
		}else{
			return new Result<List<ReCity>>("数据获取失败");
		}
	}
	/**
	 * 单个图片上传
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Result<String> uploadFileHandler(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				//获得物理路径webapp所在路径  
				String rootPath = request.getSession().getServletContext().getRealPath("");  
				String path="";
				//生成uuid作为文件名称  
		        String uuid = UUID.randomUUID().toString().replaceAll("-",""); 
		        //获得文件类型（可以判断如果不是图片，禁止上传）  
		        String contentType = file.getContentType();  
		        //获得文件后缀名称  
	            String imageName = contentType.substring(contentType.indexOf("/")+1);  
	            path = "/images/" + uuid +"."+ imageName;  
	            File serviceFile = new File(rootPath + path);
	            if(!serviceFile.exists()) serviceFile.mkdirs();
	            file.transferTo(serviceFile);  
				return new Result<String>(true,path,"上传成功");
			} catch (Exception e) {
				return new Result<String>(false,file.getOriginalFilename(),"上传失败:" + e.getMessage());
			}
		} else {
			return new Result<String>(false,file.getOriginalFilename(),"上传失败 because the file was empty");
		}
	}
	/**
	 * 上传多张图片
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/uploadFiles",method=RequestMethod.POST)  
	@ResponseBody
    public Result<List<String>> fildUpload(@RequestParam(value="file",required = false) MultipartFile[] file,  
            HttpServletRequest request){  
		List<String> listImagePath = new ArrayList<String>();
        try {
			//获得物理路径webapp所在路径  
			String rootPath = request.getSession().getServletContext().getRealPath("");
			String path = "";
			
			for (MultipartFile mf : file) {
				if (!mf.isEmpty()) {
					//生成uuid作为文件名称  
					String uuid = UUID.randomUUID().toString().replaceAll("-", "");
					//获得文件类型（可以判断如果不是图片，禁止上传）  
					String contentType = mf.getContentType();
					//获得文件后缀名称  
					String imageName = contentType.substring(contentType.indexOf("/") + 1);
					path = "/images/" + uuid + "." + imageName;
					File serviceFile = new File(rootPath + path);
		            if(!serviceFile.exists()) serviceFile.mkdirs();
					mf.transferTo(serviceFile);
					listImagePath.add(path);
				}
			} 
		} catch (Exception e) {
			return new Result<List<String>>("上传失败:" + e.getMessage());
		}
		return new Result<List<String>>(listImagePath);
    }  
	@RequestMapping(value="/getJobTypes",method={RequestMethod.GET,RequestMethod.POST})  
	@ResponseBody
	public Result<List<JobType>> getJobTypes(){
		List<JobType> jobTypes = jobTypeService.getAllJobType();
		return new Result<List<JobType>>(jobTypes);
	}
	@RequestMapping(value="/getCustomers",method={RequestMethod.GET,RequestMethod.POST})  
	@ResponseBody
	public Result<List<String>> getCustomers(){
		List<String> data = new ArrayList<String>();
		try {
			data = customerService.getCustomer("3");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result<List<String>>(true,Arrays.asList("13220286010"),e.getMessage());
		}
		return new Result<>(data);
	}
}
