package demo.web.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * parent
 * demo.web.service
 * 定时任务service
 * https://www.jb51.net/article/114291.htm
 * 需启用配置类：{@link demo.web.config.ScheduledTaskConfig}
 *
 * @author BlueDriver
 * @email cpwu@foxmail.com
 * @date 2019/03/28 10:48 Thursday
 */
@Service
public class ScheduledTaskService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    /**
     * 每隔10秒执行
     */
    @Scheduled(fixedRate = 10_000)
    public void showTime() {
        System.out.println("scheduled task: show time " + sdf.format(new Date()));
        /**
         *  某次执行期间若发生异常， 并不会影响下次的执行
         */
        //int a = 2 / 0;
    }

    /**
     * 六个部分分别表示秒、分、时、日、月、周
     */
    @Scheduled(cron = "0 0 * * * *")//the top of every hour of every day
    public void fixTimeExec() {
        System.out.println("fix time exec: " + sdf.format(new Date()));
    }
}
