package com.leo.service.schedule;

import com.github.pagehelper.PageHelper;
import com.leo.pojo.TravelSearch;
import com.leo.service.ISearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SearchSchedule
 * @Description 搜索记录定时任务类
 * @Author leo
 * @Date 2019/3/2 16:39
 * @Version 1.0
 */
@Service
public class SearchSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchSchedule.class);

    @Autowired
    private ISearchService searchService;

    /**
     * @Author leo
     * @Description 每周一早上4点执行删除冷门搜索词任务
     * @Date 17:00 2019/3/2
     */
    @Scheduled(cron = "0 0 4 ? * MON")
    public void deleteUnpopularKey() {

        LOGGER.info("=== start schedule job ===");

        PageHelper.startPage(20, 0);
        List<String> contents = searchService.getHotKeys();
        for (String content : contents) {
            searchService.deleteSearchByContent(content);
        }

        LOGGER.info("=== end schedule job ===");
    }
}
