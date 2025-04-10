package com.odtrend.infrastructure.util;

public interface GzipUtil {

    String compress(String rawString);

    String decompress(String compressedString);
}
