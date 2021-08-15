package ace;

import ace.constant.Constants;
import ace.core.AceContext;
import ace.core.AceResult;
import ace.core.AceWorker;
import ace.enums.AceScene;
import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
            put(Constants.TAG,"order_cancel");
        }};
        AceContext context = AceContext.of(dataParam, AceScene.ACE_SCENE_HOTEL_SETTLE);
        AceResult aceResult = aceWorker.classify(context);
        AceContext executorAceContext = AceContext.of(null,AceScene.ACE_SCENE_HOTEL_SETTLE);
        aceWorker.execute(executorAceContext);
    }
}
