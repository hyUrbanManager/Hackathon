package com.hy.jspider;

import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 带数据库操作。
 *
 * @author hy 2018/5/14
 */
public interface DbPipeline extends Pipeline {

    void startDb();

    void endDb();

}
