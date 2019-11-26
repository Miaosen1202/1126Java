package com.wdcloud.lms.business.quiz;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.quiz.dto.QuizDTO;
import com.wdcloud.lms.core.base.dao.QuizDao;
import com.wdcloud.lms.core.base.dao.QuizRecordDao;
import com.wdcloud.lms.core.base.model.QuizRecord;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;
import com.wdcloud.server.frame.interfaces.info.DataEditInfo;
import com.wdcloud.server.frame.interfaces.info.LinkedInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：更新测验发布状态
 *
 * @author 黄建林
 */
@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_QUIZ,
        functionName = Constants.FUNCTION_TYPE_PUBLISH
)
public class QuizPublishStatusEdit implements ISelfDefinedEdit {
    @Autowired
    private QuizDao quizDao;
    @Autowired
    private QuizRecordDao quizRecordDao;

    /**
     * @api {post} /quiz/publish/edit 更新测验发布状态
     * @apiName quizpublishStatus
     * @apiGroup Quiz
     * @apiParam {Number} id 测验信息ID
     * @apiParam {Number=0,1} status  发布状态： 1：已发布；0：未发布
     *
     * @apiExample {json} 请求示例:
     * {
     *     "id": "1",
     * 	"status": "1"
     * }
     *
     * @apiSuccess {String} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message
     * @apiSuccess {String} id 测验ID
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "1"
     * }
     */
    @Override
    public LinkedInfo edit(DataEditInfo dataEditInfo) {

        final QuizDTO dto = JSON.parseObject(dataEditInfo.beanJson, QuizDTO.class);

        List<QuizRecord> quizRecordAllList = quizRecordDao.getAllQuizRecord(dto.getId());
        int quizRecordAllListTotal = quizRecordAllList.size();

        if (quizRecordAllListTotal > 0&&dto.getStatus()==0) {
            throw new BaseException("quiz_already.submit");
        }
        quizDao.publisStatusUpdate(dto);
        return new LinkedInfo(String.valueOf(dto.getId()), dataEditInfo.beanJson);
    }
}
