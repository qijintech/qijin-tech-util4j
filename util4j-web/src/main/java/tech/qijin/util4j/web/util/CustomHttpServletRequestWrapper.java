package tech.qijin.util4j.web.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * 参考: https://howtodoinjava.com/servlets/httpservletrequestwrapper-example-read-request-body/
 *
 * @author michealyang
 * @date 2018/12/15
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();

            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                char[] charBuffer = new char[128];
                int bytesRead = -1;

                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            log.error("Error reading the request body...");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    log.error("Error closing bufferedReader...");
                }
            }
        }

        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());

        ServletInputStream inputStream = new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };

        return inputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * Use this method to read the request body N times
     */
    public String getBody() {
        return this.body;
    }
}
