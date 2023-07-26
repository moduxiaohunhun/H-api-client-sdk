package study.hapi.client;

import cn.hutool.core.util.RandomUtil;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import study.hapi.HApiApplication;
import study.hapi.Main;
import study.hapi.model.User;

import java.util.HashMap;
import java.util.Map;

import static study.hapi.utils.SignUtils.getSign;

public class YuApiClient {
    private String accessKey;

    private String secretKey;

    public YuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }





    private Map<String,String > getHeaderMap(String body){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);

//        一定不可以直接发送给后端
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 100));
        hashMap.put("sign", getSign(body, secretKey));
        return  hashMap;
    }


    public String getNameByGet(String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.get("http://localhost:8123/api/name/", paramMap);

        System.out.println(result);

        return  result;
    }


    public String getNameByPost(@RequestParam String name){
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result= HttpUtil.post("http://localhost:8123/api/name/", paramMap);

        System.out.println(result);

        return  result;
    }



    public String getUserNameByPost(@RequestBody User user){
        String json = JSONUtil.toJsonStr(user);


        HttpResponse result = HttpRequest.post("http://localhost:8123/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();

        System.out.println(result.getStatus());

        return  result.body();
    }



}
