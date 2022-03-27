package ace.enums;


import com.google.common.collect.ImmutableList;
public enum AceScene {

    ACE_SCENE_HOTEL_SETTLE("hotel_settle",ImmutableList.of("orderTagClassifier", "refundOrderClassifier"))
    ,
    ACE_SCENE_HEALTH_SETTLE("health_settle",ImmutableList.of("healthVoucherClassifier"));

    public String sceneName;
    public ImmutableList<String> classifierList;

    AceScene(String sceneName , ImmutableList<String> classifierList){
        this.sceneName = sceneName;
        this.classifierList = classifierList;
    }
}
