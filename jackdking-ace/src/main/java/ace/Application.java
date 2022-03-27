package ace;

import ace.classifier.RefundOrderClassifier;
import ace.constant.Constants;
import ace.core.AceContext;
import ace.core.AceResult;
import ace.core.AceWorker;
import ace.enums.AceScene;
import ace.executor.IExecutor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner , ApplicationContextAware {

    @Resource
    private RefundOrderClassifier refundOrderClassifier;

    private static ApplicationContext context;

    public static void main( String[] args )
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AceWorker aceWorker = AceWorker.getInstance();
        JSONObject dataParam = new JSONObject() {{
            put(Constants.TAG,"health_topic");
        }};

        AceContext context = AceContext.of(dataParam, AceScene.ACE_SCENE_HEALTH_SETTLE);
        AceResult<List<IExecutor>> aceResult = aceWorker.classify(context);

        log.info("classify result:{}",JSON.toJSONString(aceResult));

        aceResult.ifPresent((result) -> {
            AceContext executorAceContext = AceContext.of(result, AceScene.ACE_SCENE_HEALTH_SETTLE);
            List<AceResult> aceResultList =  aceWorker.execute(executorAceContext);
            log.debug("executor list result is :{}", JSON.toJSONString(aceResultList));
            aceResultList.stream().forEach(o -> {
                log.debug("result:{}",o.getResult());
            });
        });

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
