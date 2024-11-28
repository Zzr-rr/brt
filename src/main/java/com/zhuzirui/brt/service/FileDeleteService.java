package com.zhuzirui.brt.service;

import java.io.IOException;

public interface FileDeleteService {
    boolean deleteFile(String fileUrl) throws IOException;
}
