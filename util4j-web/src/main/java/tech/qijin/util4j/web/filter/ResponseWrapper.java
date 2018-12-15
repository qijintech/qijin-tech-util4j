package tech.qijin.util4j.web.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author weisenqiu
 * @version 1.0
 * @created 17/1/12
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream outputStream;
    private ServletOutputStream servletOutputStream;

    public ResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        outputStream = new ByteArrayOutputStream();
        final ServletOutputStream responseOutputStream = response.getOutputStream();
        servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
                responseOutputStream.write(b);
            }
        };
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(outputStream);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }


    public String getContent() throws IOException {
        return new String(outputStream.toByteArray(), "utf-8");
    }
}
