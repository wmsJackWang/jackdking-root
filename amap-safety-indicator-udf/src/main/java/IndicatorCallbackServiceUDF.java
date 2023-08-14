import com.aliyun.odps.udf.ExecutionContext;
import com.aliyun.odps.udf.UDF;
import com.aliyun.odps.udf.UDFException;
import com.amap.safety.model.base.ServiceResponse;
import com.amap.safety.service.IndicatorPlatformCallbackService;
import com.taobao.hsf.app.api.util.HSFApiConsumerBean;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName IndicatorCallbackService
 * @Description TODO
 * @Author jackdking
 * @Date 03/08/2023 10:52 上午
 * @Version 2.0
 **/
public class IndicatorCallbackServiceUDF extends UDF {
  // HSF service full name

  private static final String HSF_SERVICE_NAME = "com.amap.safety.service.IndicatorPlatformCallbackService";

  // HSF service version

  private static final String HSF_SERVICE_VERSION = "1.0.0.prepub";

  // HSF service group

  private static final String HSF_SERVICE_GROUP = "HSF";


  public static IndicatorPlatformCallbackService service;

  static {
    try {
      service = initServices();
      Thread.sleep(5000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static IndicatorPlatformCallbackService initServices() {

    try {

      HSFApiConsumerBean hsfApiConsumerBean = new HSFApiConsumerBean();

      hsfApiConsumerBean.setInterfaceName(HSF_SERVICE_NAME);

      hsfApiConsumerBean.setVersion(HSF_SERVICE_VERSION);

      hsfApiConsumerBean.setGroup(HSF_SERVICE_GROUP);

      hsfApiConsumerBean.setMaxWaitTimeForCsAddress(60000);

      //hsfApiConsumerBean.init(true);

      hsfApiConsumerBean.init(10000L);

      return (IndicatorPlatformCallbackService) hsfApiConsumerBean.getObject();

    } catch (Exception e) {

      e.printStackTrace(System.out);

      return null;

    }

  }

  public String evaluate(String params) {

    ServiceResponse serviceResponse = service.callback(params);


    return String.valueOf(serviceResponse.getData());

  }

  @Override

  public void setup(ExecutionContext ctx) throws UDFException {

    super.setup(ctx);

  }

  @Override

  public void close() throws UDFException {

    super.close();

  }
}
