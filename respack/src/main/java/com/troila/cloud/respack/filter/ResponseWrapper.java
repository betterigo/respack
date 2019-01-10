package com.troila.cloud.respack.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    
    
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
    
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
    	filterOutput = new MyServletOutputStream(bytes); 
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
    
    public void writeFileStream(OutputStream out) {
    	
    }

    class MyServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream os ;

        public MyServletOutputStream(ByteArrayOutputStream ostream) {
            this.os = ostream;
        }

        @Override
        public void write(int b) throws IOException {
            os.write(b);
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
