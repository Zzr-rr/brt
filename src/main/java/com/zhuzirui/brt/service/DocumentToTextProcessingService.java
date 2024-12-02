package com.zhuzirui.brt.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public interface DocumentToTextProcessingService {
    public String convertToText(File fileContent);
}
