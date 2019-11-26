package com.wdcloud.lms;

import com.google.common.collect.Lists;
import com.wdcloud.lms.base.dto.AssignUserOparateDTO;
import com.wdcloud.lms.base.service.ModuleCompleteService;
import com.wdcloud.lms.config.AppConfig;
import com.wdcloud.lms.core.base.dao.ModuleDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.ModuleItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})// 指定启动类
@Import(AppConfig.class)
public class ModuleCompleteTest {

    @Autowired
    private ModuleCompleteService moduleService;
    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private ModuleDao moduleDao;

    /**
     * 测试添加单元方法
     */
    @Test
    public void t1() {
        Long moduleId = 112L;
        Module module = moduleDao.get(moduleId);
        moduleService.addModule(module);
        System.out.println("");
    }

    /**
     * 测试添加任务到单元方法
      */
    @Test
    public void t2() {
        Long courseId = 188L;
        Long moduleItemId = 97L;
        moduleService.addAssignmentToModule(moduleItemId, courseId);
        System.out.println("");
    }

    /**
     * 测试完成/查看任务
     */
    @Test
    public void t3() {
        Long originId = 119L;
        Integer originType = 2;
        moduleService.completeAssignment(originId, originType);
        System.out.println("");
    }

    /**
     * 测试删除单元项
     */
    @Test
    public void t4() {
        Long moduleItemId = 119L;
        moduleService.deleteModuleItem(moduleItemId);
        System.out.println("");
    }

    /**
     * 测试移动单元项
     */
    @Test
    public void t5() {
        Long moduleItemId = 97L;
        Long moduleIdNow = 112L;
        moduleService.moveModuleItem(moduleItemId, moduleIdNow);
        System.out.println("");
    }

    /**
     * 测试编辑任务分配
     */
    @Test
    public void t6() {
        moduleService.updateAssign(213L, 11L,1);
        System.out.println("");
    }

    /**
     * 测试删除单元
     */
    @Test
    public void t7() {
        Long moduleId = 110L;
        moduleService.deleteModule(moduleId);
        System.out.println("");
    }

    /**
     * 测试班级添加/删除人员
     */
    @Test
    public void t8() {
        AssignUserOparateDTO build = AssignUserOparateDTO.builder()
                .oparateType(1)
                .courseId(110L)
                .srcSectionId(11l)
                .userId(11L)
                .build();
        moduleService.addSectionUser(build);
        System.out.println("");
    }

    /**
     * 测试处理上线前历史数据
     */
    @Test
    public void t9() {
        moduleService.initHistoryData();
        System.out.println("");
    }


}
