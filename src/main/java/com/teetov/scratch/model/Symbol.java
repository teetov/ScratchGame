package com.teetov.scratch.model;

import com.teetov.scratch.exception.ScratchGameException;

public abstract class Symbol {

    protected final String name;

    protected Symbol(String name) {
        if (name == null || name.isEmpty()) {
            throw new ScratchGameException("Name of one of symbol empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
