package com.hy.jspider;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by Administrator on 2018/5/13.
 *
 * @author hy 2018/5/13
 */
public class DatabasePipeline implements Pipeline {

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("start store in Database.");

    }

}
