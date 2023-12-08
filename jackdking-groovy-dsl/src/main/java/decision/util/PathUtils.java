package com.lesofn.util;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lesofn
 * @version 1.0 2021-03-02 11:59
 */
public class PathUtils {
    private static final String PATH_PREFIX_FILE = "file:";
    private static final String SPRINGBOOT_MARK = "!";
    private static final String SPRINGBOOT_BOOT = "BOOT-INF";

    public static String parseFilePath(URL url) {
        if (url == null) {
            return "";
        }

        String filePath = url.getPath();
        if (!StringUtils.startsWith(filePath, PATH_PREFIX_FILE)) {
            return filePath;
        }

        filePath = StringUtils.replace(filePath, SPRINGBOOT_MARK, "");
        List<String> nodeList = new ArrayList<>(Splitter.on(File.separator).splitToList(filePath));
        int bootIndex = nodeList.indexOf(SPRINGBOOT_BOOT);
        if (bootIndex > 0) {
            nodeList.remove(bootIndex - 1);
        }
        filePath = StringUtils.join(nodeList, File.separator);
        return StringUtils.replace(filePath, PATH_PREFIX_FILE, "");
    }
}

