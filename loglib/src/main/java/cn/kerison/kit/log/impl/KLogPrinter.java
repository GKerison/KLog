package cn.kerison.kit.log.impl;

import android.util.Log;

import cn.kerison.kit.log.ILogPrinter;

/**
 * Created by kerison
 */
public class KLogPrinter implements ILogPrinter {

    public static class LogLevel {
        /**
         * Log level value {@link Log}
         */
        public static final int VERBOSE = 2;
        public static final int DEBUG = 3;
        public static final int INFO = 4;
        public static final int WARN = 5;
        public static final int ERROR = 6;
        public static final int ASSERT = 7;
//        public static final int ALL = Integer.MIN_VALUE;
//        public static final int NONE = Integer.MAX_VALUE;
    }

    /**
     * 避免日志过多显示不全
     */
    private static final int CHUNK_SIZE = 2048;
    /**
     * 栈内有效函数偏移
     */
    private static final int STACK_OFFSET = 6;
    /**
     * 系统的默认换行符
     */
    private static final String LR = System.getProperty("line.separator");

    /**
     * Drawing Frame
     */
    private static final char TOP_LEFT_BAR = '╔';
    private static final char BOTTOM_LEFT_BAR = '╚';
    private static final char MIDDLE_LEFT_BAR = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String TOP_BORDER = TOP_LEFT_BAR + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_BAR + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = String.valueOf(MIDDLE_LEFT_BAR) ;

    private String mTag;
    private boolean isShowThread;
    private boolean isShowPackage;
    private int mStackCount;


    public KLogPrinter() {
        this.mTag = "KL☞";
        this.mStackCount = 2;
        this.isShowPackage = true;
        this.isShowThread = true;
    }

    @Override
    public ILogPrinter setTag(final String tag) {
        this.mTag = tag;
        return this;
    }

    @Override
    public ILogPrinter setMethodCount(final int count) {
        this.mStackCount = count;
        return this;
    }

    @Override
    public ILogPrinter hideExtraInfo() {
        this.mStackCount = 0;
        this.isShowPackage = false;
        this.isShowThread = false;
        return this;
    }

    @Override
    public void v(final String msg) {
        log(LogLevel.VERBOSE, msg);
    }

    @Override
    public void d(final String msg) {
        log(LogLevel.DEBUG, msg);
    }

    @Override
    public void i(final String msg) {
        log(LogLevel.INFO, msg);
    }

    @Override
    public void w(final String msg) {
        log(LogLevel.WARN, msg);
    }

    @Override
    public void e(final String msg) {
        log(LogLevel.ERROR, msg);
    }

    @Override
    public void wtf(final String msg) {
        log(LogLevel.ASSERT, msg);
    }

    @Override
    public void text(final String msg) {
        log(LogLevel.INFO, msg);
    }

    /**
     * 打印log信息
     *
     * @param level
     * @param msg
     */
    private synchronized void log(int level, String msg) {

        showTopBar(level);

        if (this.isShowThread) {
            showTheadInfo(level);
        }

        if (mStackCount > 0) {
            showStackTrace(level);
        }

        showContent(level, msg);

        showBottomBar(level);
    }

    /**
     * 打印顶部线
     *
     * @param level
     */
    private void showTopBar(int level) {
        print(level, mTag, TOP_BORDER);
    }

    /**
     * 打印底部线
     *
     * @param level
     */
    private void showBottomBar(int level) {
        print(level, mTag, BOTTOM_BORDER);
    }

    /**
     * 打印线程信息
     *
     * @param level
     */
    private void showTheadInfo(int level) {
        print(level, mTag, String.format("%sThread:%s[%s]", MIDDLE_LEFT_BAR, Thread.currentThread().getName(), Thread.currentThread().getId()));
    }


    /**
     * 打印分隔线
     *
     * @param level
     */
    private void showDivider(int level) {
        print(level, mTag, MIDDLE_BORDER);
    }

    /**
     * 打印栈信息
     *
     * @param level
     */
    private void showStackTrace(int level) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        //避免超出栈的有效范围
        int count = trace.length - STACK_OFFSET;
        if (count > mStackCount) {
            count = mStackCount;
        }

        for (int i = count; i > 0; i--) {
            StackTraceElement element = trace[STACK_OFFSET + i - 1];

            StringBuilder builder = new StringBuilder();
            builder.append(MIDDLE_LEFT_BAR).append(isShowPackage ? element.getClassName() : getSimpleClassName(element.getClassName()))
                    .append(".")
                    .append(element.getMethodName())
                    .append(" ")
                    .append("(")
                    .append(element.getFileName())
                    .append(":")
                    .append(element.getLineNumber())
                    .append(")");
            print(level, mTag, builder.toString());
        }

        if (count > 0) {
            showDivider(level);
        }
    }

    /**
     * 获取类名
     *
     * @param name
     * @return
     */
    private String getSimpleClassName(String name) {
        if (name != null) {
            int lastIndex = name.lastIndexOf(".");
            if (lastIndex == -1) {
                return null;
            } else {
                return name.substring(lastIndex + 1);
            }
        } else {
            return null;
        }
    }

    /**
     * 显示内容
     *
     * @param level
     * @param msg
     */
    private void showContent(int level, String msg) {
        byte[] bytes = msg.getBytes();
        int length = bytes.length;

        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            formatContent(level, mTag, new String(bytes, i, count));
        }
    }

    /**
     * 格式化内容 主要是换行操作
     *
     * @param level
     * @param tag
     * @param content
     */
    private void formatContent(int level, String tag, String content) {
        String[] lines = content.split(LR);
        for (String line : lines) {
            print(level, tag, MIDDLE_LEFT_BAR + line);
        }
    }

    /**
     * 打印
     *
     * @param level 等级
     * @param tag   标签
     * @param msg   内容
     */
    private static void print(int level, String tag, String msg) {
        Log.println(level, tag, msg);
    }
}
