package com.jit.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyTask
 * @description: 定时任务
 * @author: XZQ
 * @create: 2020/3/31 19:25
 **/
@Component
public class MyTask {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每小时执行一次
     */
    @Scheduled(cron = "0 0 6,12,18 * * ?")
    public void refreshHotBlogs() {
        redisTemplate.delete("hotBlogList");
        redisTemplate.delete("newBlogList");
        redisTemplate.delete("hotTagList");
        redisTemplate.delete("hotTypeList");
        System.out.println("清除缓存" + System.currentTimeMillis());
    }

}
