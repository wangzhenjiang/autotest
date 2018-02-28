package com.wzj.learn.autotest.testcase;

import com.google.gson.*;
import com.wzj.learn.autotest.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 请填写类的描述
 *
 * @author wangzhenjiang
 * @date 2018-01-15 16:38
 */
public class CountWorkTimeTest {
    public static void main(String[] args) throws ParseException {
        String fileName1 = "D:\\Work\\Docs\\2017打卡记录\\2017001.txt";
        String fileName2 = "D:\\Work\\Docs\\2017打卡记录\\2017002.txt";
        String fileName3 = "D:\\Work\\Docs\\2017打卡记录\\2017003.txt";
        String fileName4 = "D:\\Work\\Docs\\2017打卡记录\\2017004.txt";
        Map<String, Integer> map = getAllWorkTime(fileName1, fileName2, fileName3, fileName4);
        System.err.println(map.toString());
        Integer totalWorkTime = map.get("totalWorkTime");
        Integer workTimeCnt = map.get("workTimeCnt");
        Integer zmTotalWork = map.get("zmTotalWork");
        Integer zmTotalWorkCnt = map.get("zmTotalWorkCnt");

        System.err.println("平均加班时长：" + totalWorkTime / workTimeCnt);
        System.err.println("周末平均加班时长：" + zmTotalWork / zmTotalWorkCnt);

    }

    private static Map<String, Integer> getAllWorkTime(String... fileNames) throws ParseException {
        Integer totalWorkTime = 0;
        Integer workTimeCnt = 0;
        Integer zmTotalWork = 0;
        Integer zmTotalWorkCnt = 0;
        Map<String, Integer> total = new HashMap<>();
        for (String fileName : fileNames) {
            Map<String, Integer> workTime = getWorkTime(new File(fileName));
            totalWorkTime += workTime.get("totalWorkTime");
            workTimeCnt += workTime.get("workTimeCnt");
            zmTotalWork += workTime.get("zmTotalWork");
            zmTotalWorkCnt += workTime.get("zmTotalWorkCnt");
        }
        total.put("totalWorkTime", totalWorkTime);
        total.put("workTimeCnt", workTimeCnt);
        total.put("zmTotalWork", zmTotalWork);
        total.put("zmTotalWorkCnt", zmTotalWorkCnt);
        return total;
    }

    public static Map<String, Integer> getWorkTime(File file) throws ParseException {
        String content = FileUtil.read(file);
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonEle = jsonParser.parse(content);
        JsonObject jsonObj = jsonEle.getAsJsonObject();
        JsonArray jsonArr = jsonObj.getAsJsonArray("aaData");
        Integer totalWorkTime = 0;
        Integer workTimeCnt = 0;
        Integer zmTotalWork = 0;
        Integer zmTotalWorkCnt = 0;
        for (JsonElement ele : jsonArr) {
            JsonObject obj = ele.getAsJsonObject();
            String day = getTime(obj, "attendanceDay");
            String attendanceStartTime = getTime(obj, "attendanceStartTime");
            String attendanceEndTime = getTime(obj, "attendanceEndTime");
            if (StringUtils.isBlank(attendanceStartTime)
                    || StringUtils.isBlank(attendanceEndTime)) {
                continue;
            }
            String qingjia = getTime(obj, "applyReason");
            if (StringUtils.isNotBlank(qingjia)
                    && !"正常".equals(qingjia)
                    && !"休息日".equals(qingjia)
                    && !"节假日".equals(qingjia)) {
                System.err.println(day + "【请假】");
                continue;
            }
            Date startTime = DateUtils.parseDate(day + " " + attendanceStartTime, "yyyy.MM.dd HH:mm:ss");
            Date currStartTime = DateUtils.parseDate(day + " " + "9:00:00", "yyyy.MM.dd HH:mm:ss");
            if (startTime.before(currStartTime)) {
                startTime = currStartTime;
            }
            Date endTime = DateUtils.parseDate(day + " " + attendanceEndTime, "yyyy.MM.dd HH:mm:ss");
            boolean isWeekend = isWeekend(obj);
            Long mins = TimeUnit.MILLISECONDS.toMinutes(endTime.getTime() - startTime.getTime()) - (isWeekend ? 0 : 9 * 60);
            if (isWeekend) {
                zmTotalWork = zmTotalWork + mins.intValue();
                zmTotalWorkCnt++;
            }else{
                totalWorkTime = totalWorkTime + mins.intValue();
                workTimeCnt++;
            }
            System.err.println(day + "(" + attendanceStartTime + " - " + attendanceEndTime + ")" + " 加班： " + mins + "分钟。" + (isWeekend ? "【周末】" : ""));
        }
        Map<String, Integer> workTime = new HashMap<>();
        workTime.put("totalWorkTime", totalWorkTime);
        workTime.put("workTimeCnt", workTimeCnt);
        workTime.put("zmTotalWork", zmTotalWork);
        workTime.put("zmTotalWorkCnt", zmTotalWorkCnt);
        return workTime;

    }

    private static boolean isWeekend(JsonObject obj) {
        JsonElement element = obj.get("weekDay");
        String weekDay = element.getAsString();
        return "日".equals(weekDay) || "六".equals(weekDay);
    }

    private static String getTime(JsonObject obj, String key) {
        JsonElement element = obj.get(key);
        if (element == null) {
            return null;
        }
        return element.getAsString();
    }
}
