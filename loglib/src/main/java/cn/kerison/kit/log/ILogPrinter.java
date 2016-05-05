package cn.kerison.kit.log;

/**
 * Created by kerison
 */
public interface ILogPrinter {

    ILogPrinter setTag(String tag);

    ILogPrinter setMethodCount(int count);

    ILogPrinter hideExtraInfo();

    void v(String msg);

    void d(String msg);

    void i(String msg);

    void w(String msg);

    void e(String msg);

    void wtf(String msg);

    void text(String msg);
}
