package com.wdcloud.lms.business.strategy.update;

import com.wdcloud.lms.business.strategy.Context;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public class UpdateContext extends Context {
    private UpdateStrategy strategy;

    public UpdateContext(UpdateStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute(Long id, String name) {
        strategy.updateName(id, name);
    }

    public void updatePublishStatus(Long id, Integer status) {
        strategy.updatePublishStatus(id, status);
    }

    public void execute(Long id, String name, int score, boolean isPublish) {
        strategy.updateNameAndScoreAndStatus(id, name, score, isPublish);
    }

    public void execute(Long id, String name, String url) {
        strategy.updateNameAndUrl(id, name, url);
    }
}
