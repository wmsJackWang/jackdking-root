package ace.conf;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Copyright (C) 阿里巴巴
 *
 * @ClassName AcePropety
 * @Description TODO
 * @Author jackdking
 * @Date 11/12/2021 5:09 下午
 * @Version 2.0
 **/
@Data
@NoArgsConstructor
public class AceProperty {
    private List<String> attributorsPath;
    private List<String> classifiersPath;
    private List<String> executorsPath;
}