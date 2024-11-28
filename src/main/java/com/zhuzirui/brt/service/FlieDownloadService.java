package com.zhuzirui.brt.service;


import java.io.File;

import java.io.IOException;

public interface FlieDownloadService {
     File downloadFile(String fileName) throws IOException;
}
