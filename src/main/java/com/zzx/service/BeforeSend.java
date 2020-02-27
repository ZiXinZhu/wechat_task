package com.zzx.service;

import com.zzx.bean.Cache;
import com.zzx.tools.UploadTool;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Data
public class BeforeSend implements Runnable {

    private String token;
    private String path;
    private String template;
    private int threadNum;

    public BeforeSend(String token, String template, String path, int threadNum) {
        this.token = token;
        this.template = template;
        this.path = path;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        sendPublicMessage(template, token, path, threadNum);
    }

    public void sendPublicMessage(String template, String token, String path, int threadNum) {
//      String template = "{\"touser\":\""+list.get(i)+"\",\"data\":{\"first\":{\"color\":\"#1AAD19\",\"value\":\" \"},\"keyword1\":{\"color\":\"#313131\",\"value\":\"跑哪儿2020马拉松新年签\"},\"keyword2\":{\"color\":\"#313131\",\"value\":\"即日起至1月26日\"},\"remark\":{\"color\":\"#1328DB\",\"value\":\"10万+跑友抽取了自己的2020跑马幸运签，快来看看你的吧！戳此立即抽签\"}},\"miniprogram\":{\"appid\":\"wx95e8497d04f81a82\",\"pagepath\":\"activity/pages/NewyearDraw/index?a=1&b=2&ald_media_id=53726&ald_link_key=3249cd83c2e81e71\"},\"template_id\":\"uqUtSOFwnU6URZM90LxbwYJjv1gfc8SemtKKUmXfVRI\"}";
        Map<Integer, List<String>> maps;
        if (CollectionUtils.isEmpty(Cache.cacheList)) {
            maps = UploadTool.upload(path);
            Cache.cacheList = new ConcurrentHashMap<>(maps);
        } else {
            maps = new ConcurrentHashMap<>(Cache.cacheList);
        }


        ExecutorService executorService = Executors.newWorkStealingPool(10);
        if (maps == null || maps.size() == 0) {
            return;
        }
        int size = 0;
        if (maps.size() <= threadNum) {
            size = 1;
            threadNum = 1;
        } else {
            size = maps.size() / threadNum;
        }

        for (int i = 0; i < threadNum; i++) {
            if (i == threadNum - 1) {
                MessageSendTask messageSendTask = new MessageSendTask(maps, template, token);
                executorService.submit(messageSendTask);
            } else {
                Map<Integer, List<String>> map = new ConcurrentHashMap<>();
                int count = 0;
                for (Map.Entry<Integer, List<String>> entry : maps.entrySet()) {

                    if (count == size) {
                        break;
                    }
                    map.put(entry.getKey(), entry.getValue());
                    maps.remove(entry.getKey());
                    count++;
                }

                MessageSendTask messageSendTask = new MessageSendTask(map, template, token);
                executorService.submit(messageSendTask);
            }

        }
    }





/*
    public List<String> getOpenId() throws IOException {
        BufferedReader bufferedReader= new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\id.txt"));
        String lineTxt = "";
        List<String> list=new ArrayList<>();
        while((lineTxt = bufferedReader.readLine()) != null){
            list.add(lineTxt);
        }
        return list;
    }
*/

}
