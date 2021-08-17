package ace;

import ace.constant.Constants;
import ace.core.AceContext;
import ace.core.AceResult;
import ace.core.AceWorker;
import ace.enums.AceScene;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AceWorker aceWorker = AceWorker.getInstance();
        JSONObject dataParam = new JSONObject() {{
            put(Constants.TAG,"order_ready");
        }};
        AceContext context = AceContext.of(dataParam, AceScene.ACE_SCENE_HOTEL_SETTLE);
        AceResult aceResult = aceWorker.classify(context);
        log.info("classify result:{}",JSON.toJSONString(aceResult));
        AceContext executorAceContext = AceContext.of(aceResult.getResult(),AceScene.ACE_SCENE_HOTEL_SETTLE);
        List<AceResult> aceResultList =  aceWorker.execute(executorAceContext);
        log.debug("executor list result is :{}", JSON.toJSONString(aceResultList));
    }
}
