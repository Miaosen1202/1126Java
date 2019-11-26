package com.wdcloud.lms.business.strategy.delete;

import java.util.Collection;

public class DeleteContext {
    private DeleteStrategy strategy;

    public DeleteContext(DeleteStrategy strategy) {
        this.strategy = strategy;
    }

    public Integer delete(Long id) {
        return this.strategy.delete(id);
    }

    public Integer delete(Collection<Long> ids) {
        return this.strategy.delete(ids);
    }
}
