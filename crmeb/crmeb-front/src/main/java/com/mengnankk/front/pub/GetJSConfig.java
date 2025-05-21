package com.mengnankk.front.pub;


import com.mengnankk.common.model.system.SystemConfig;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/public/jsconfig")
@Api(tags = "公共api设置")
public class GetJSConfig {

}
