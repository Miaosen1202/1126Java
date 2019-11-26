package com.wdcloud.lms.business.strategy.query;

public class QueryContext {
    private QueryStrategy strategy;

    public QueryContext(QueryStrategy strategy) {
        this.strategy = strategy;
    }

    public OriginData query(Long id) {
        return this.strategy.query(id);
    }
}
