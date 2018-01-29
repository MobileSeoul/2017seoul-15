package com.stm.repository.remote.custom;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * 프로그레스 요청 Body 클래스
 * <p>
 * 파일 전송 Stream 처리 Callback 정보
 */
public class ProgressRequestBody extends RequestBody {

    private File file;
    private String contentType;
    private int DEFAULT_BUFFER_SIZE = 4084;

    private final ProgressListener progressListener;

    public ProgressRequestBody(File file, String contentType, ProgressListener progressListener) {
        this.file = file;
        this.contentType = contentType;
        this.progressListener = progressListener;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse(contentType);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        this.progressListener.onStartProgress(this.file.length());
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream fileInputStream = new FileInputStream(file);
        long bytesRead = 0;
        try {
            int read;
            while ((read = fileInputStream.read(buffer)) != -1) {
                this.progressListener.onUpdateProgress(bytesRead);
                bytesRead += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            this.progressListener.onDestroyProgress();
            fileInputStream.close();
        }
    }
}
