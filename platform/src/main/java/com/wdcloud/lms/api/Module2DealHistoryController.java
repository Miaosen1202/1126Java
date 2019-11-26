package com.wdcloud.lms.api;

import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.server.frame.api.utils.response.Code;
import com.wdcloud.server.frame.api.utils.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class Module2DealHistoryController {

    @Autowired
    private ModuleCompleteService moduleService;

    @PostMapping("module2/history/deal")
    public String rootOrgInit(@RequestBody Map<String,String> map) {
        moduleService.initHistoryData();
        return Response.returnResponse(Code.OK, "success");
    }
}
