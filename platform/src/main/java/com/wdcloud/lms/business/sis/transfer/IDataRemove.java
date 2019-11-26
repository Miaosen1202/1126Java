package com.wdcloud.lms.business.sis.transfer;

import com.wdcloud.lms.core.base.model.StudyGroupUser;

import java.util.List;

public interface IDataRemove<T> {

    int remove(List<T> condition);


    class StudyGroupUserRm implements IDataRemove<Long> {
        @Override
        public int remove(List<Long> courseIds) {
            return 0;
        }
    }
}
