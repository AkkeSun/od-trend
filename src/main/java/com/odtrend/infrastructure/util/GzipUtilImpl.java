package com.odtrend.infrastructure.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.springframework.stereotype.Component;

@Component
public class GzipUtilImpl implements GzipUtil {

    @Override
    public String compress(String rawString) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            GZIPOutputStream zo = new GZIPOutputStream(bo);
            BufferedOutputStream bos = new BufferedOutputStream(zo);

            bos.write(rawString.getBytes());
            bos.close();
            zo.close();

            return Base64.getEncoder().encodeToString(bo.toByteArray());
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String decompress(String compressedString) {
        try {
            byte[] compressed = Base64.getDecoder().decode(compressedString.getBytes());
            ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
            GZIPInputStream gzipInputStream = new GZIPInputStream(bais);
            BufferedReader br = new BufferedReader(new InputStreamReader(gzipInputStream,
                StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            gzipInputStream.close();
            bais.close();
            return builder.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
