package com.troila.cloud.respack.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper {


    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    private PrintWriter printWriter;
    private ServletOutputStream filterOutput;
    private ServletOutputStream servletOutputStream;
    private boolean hasWriteBytes = false;
    
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        try {
			this.servletOutputStream = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
    	filterOutput = new MyServletOutputStream(bytes,servletOutputStream); 
    	return filterOutput;
    }
  
    @Override
    public PrintWriter getWriter() throws IOException {
        try{
        	printWriter = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return printWriter;
    }

    public byte[] getBytes() {
        if(printWriter != null) {
        	printWriter.close();
        } 

        if(bytes != null) {
            try {
                bytes.flush();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        return bytes.toByteArray();
    }
    
    public void writeFileStream(HttpServletResponse response) throws IOException {
//    	response.addIntHeader("Content-Length", bytes.size());
//    	ServletOutputStream os = response.getOutputStream();
//    	bytes.writeTo(os);
//    	os.w
//    	this.servletOutputStream.
    	this.servletOutputStream.flush();
    }

    class MyServletOutputStream extends ServletOutputStream {
    	
    	private static final int TOTAL_SIZE = 1 * 1024 * 1024;//1m的json字符串长度限制
    	
        private ByteArrayOutputStream os ;

        private ServletOutputStream servletOutputStream;
        
        public MyServletOutputStream(ByteArrayOutputStream ostream) {
            this.os = ostream;
        }

        
        
		public MyServletOutputStream(ServletOutputStream servletOutputStream) {
			super();
			this.servletOutputStream = servletOutputStream;
		}

		public MyServletOutputStream(ByteArrayOutputStream os, ServletOutputStream servletOutputStream) {
			super();
			this.os = os;
			this.servletOutputStream = servletOutputStream;
		}



		@Override
        public void write(int b) throws IOException {
			//应该设定os最大读取的字节数。避免oom
			if(os.size()<TOTAL_SIZE) {				
				os.write(b);//此处需要判断是否是json字符串,如果不是json，那么也需要直接使用servletOutputStream输出
			}else {
				//超过最大阈值会直接输出
				if(!hasWriteBytes) {					
					servletOutputStream.write(os.toByteArray(), 0, os.size());
					hasWriteBytes = true;
				}
				servletOutputStream.write(b);
			}
        }

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setWriteListener(WriteListener listener) {
			
		}

    }
	
}
