package cn.kerison.kit.log.impl;

import cn.kerison.kit.log.ILogPrinter;

/**
 * Created by kerison
 */
public class NoLogPrinter implements ILogPrinter {

    @Override
    public ILogPrinter setTag(final String tag) {
        return this;
    }

    @Override
    public ILogPrinter setMethodCount(final int count) {
        return this;
    }

    @Override
    public ILogPrinter hideExtraInfo() {
        return this;
    }

    @Override
    public void v(final String msg) {

    }

    @Override
    public void d(final String msg) {

    }

    @Override
    public void i(final String msg) {

    }

    @Override
    public void w(final String msg) {

    }

    @Override
    public void e(final String msg) {

    }

    @Override
    public void wtf(final String msg) {

    }

    @Override
    public void text(final String msg) {

    }
}
