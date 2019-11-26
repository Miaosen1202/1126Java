package com.wdcloud.lms;

import com.wdcloud.lms.business.grade.GradeBookQuery;
import com.wdcloud.lms.business.grade.GradeUserQuery;
import com.wdcloud.lms.config.AppConfig;
import com.wdcloud.lms.core.base.dao.AssignmentGroupItemDao;
import com.wdcloud.lms.core.base.dao.ModuleItemDao;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.lms.util.MailService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServiceApplication.class})// 指定启动类
@Import(AppConfig.class)
public class MyTest {

    @Autowired
    private ModuleItemDao moduleItemDao;
    @Autowired
    private AssignmentGroupItemDao assignmentGroupItemDao;
    @Autowired
    private GradeUserQuery gradeUserQuery;
    @Autowired
    private GradeBookQuery gradeBookQuery;
    @Autowired
    private com.wdcloud.lms.business.grade.SectionSubmissionSummaryQuery sectionSubmissionSummaryQuery;
    @Test
    public void t1() {
        List<ModuleItem> order = moduleItemDao.getModuleItemOrderBySeq(2L);
        order.forEach(o -> moduleItemDao.updateSeq(o.getId(), o.getModuleId(), o.getSeq() + 1));
//        order.forEach(o -> o.setSeq(o.getSeq()-2));
//        moduleItemDao.updateBatchSeqAndModuleId(order);

    }

    @Test
    public void t2() {
        Map<String, String> map =new HashMap<String, String>();
          map.put("userId","12");
        map.put("sectionId","405");
        map.put("courseId","317");
//        gradeUserQuery.search(map);
//        gradeBookQuery.search(map);
        sectionSubmissionSummaryQuery.search(map);

    }

    @Autowired
    private MailService mailService;
    @Test
    public void sendEmail() {
        try {
            String content =
                    "<h2>设备ID：" + 1111111111+ "</h2>"
                    + "<h2 style='color:red;'>" + "温度" + "报警数值：" + 35 + "</h2>"
                    + "<h2>报警阀值：" + 30 + "</h2>"
                    + "<h2>报警对象：" + "lms" + "</h2>";
            mailService.sendSimpleEmail("wangff@wdcloud.cc","报警",content);
//            Context context = new Context();
//            context.setVariable("url", "https://github.com/Javen205/IJPay");
//            context.setVariable("author","wff");
//            context.setVariable("project","lms");
//            mailService.sendTemplateEmail("wangff@wdcloud.cc ","这是主题","emailTemp",context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }











    public static void main(String[] args) throws Exception {
        String d1 = "2019-10-10T11:11:11";
        String d2 = "2019-10-10 11:11:11";
        String d3 = "2019-10-10 11:11:11.222";
////        String d4 = "2019-10-10 11:11:11";
//        System.out.println(new DateTime(d1).toDate());
//        System.out.println(new DateTime(d2).toDate());
//        System.out.println(new DateTime(d3).toDate());
//
        String[] pars = new String[]{"YYYY-MM-DD HH:mm:ss", "YYYY-MM-DD'T'HH:mm:ss", "YYYY-MM-DD HH:mm:ss.SSS", "YYYY-MM-DD HH:mm:ss.SSSZZ"};
        DateUtils.parseDate(d1, pars);
        DateUtils.parseDate(d2, pars);
        DateUtils.parseDate(d3, pars);
//        ZipFile zipFile = new ZipFile("");
//        Enumeration<? extends ZipEntry> entries = zipFile.entries();
//        while (entries.hasMoreElements()) {
//            ZipEntry zipEntry = entries.nextElement();
//            if (!zipEntry.isDirectory()) {
//                File targetFile = new File("" + "/" + zipEntry.getName());
//                targetFile.createNewFile();
//
//                Files.copy(zipFile.getInputStream(zipEntry), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            }
//        }
    }
}
