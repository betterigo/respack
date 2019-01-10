package com.troila.cloud.respack.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.troila.cloud.respack.core.AttrsSelector;
import com.troila.cloud.respack.core.PackEntity;
import com.troila.cloud.respack.core.RespAttrs;
import com.troila.cloud.respack.core.ResultPackager;
import com.troila.cloud.respack.exception.BaseErrorException;

/**
 * 该filter将负责包装返回结果
 * @author haodonglei
 *
 */
public class ResultPackFilter extends OncePerRequestFilter{

	private static final Logger logger = LoggerFactory.getLogger(ResultPackFilter.class);
	
	private AttrsSelector attrsSelector;
	
	private ResultPackager resultPackager;
	
	private List<String> ignorePaths;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public ResultPackFilter(AttrsSelector attrsSelector, ResultPackager resultPackager, List<String> ignorePaths) {
		super();
		this.attrsSelector = attrsSelector;
		this.resultPackager = resultPackager;
		this.ignorePaths = ignorePaths;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = request.getRequestURI();
		boolean hasError = false;
		if(!matchUri(uri)) {			
			ResponseWrapper wrapper = new ResponseWrapper(response);
			RespAttrs respAttrs = null;
			try {
				filterChain.doFilter(request, wrapper);
				//后续操作
				//1.获取response中的属性信息
				respAttrs = attrsSelector.selectResponseAtts(wrapper);
			} catch (Exception e) {
				if(e.getCause() instanceof BaseErrorException) {
					hasError = true;
					BaseErrorException be = (BaseErrorException)e.getCause();
					logger.info("error_code:{}",be.getErrorCode());
					respAttrs = attrsSelector.selectExceptionAtts(wrapper, be);
				}else {
					throw e;
				}
			}
			//获取返回的消息体 目前只处理application/json类型
			if(response.getContentType() != null && response.getContentType().contains("application/json") || hasError) {				
				String result = new String(wrapper.getBytes(),"utf-8");
				JsonNode root = mapper.readTree(result);
				PackEntity entity = resultPackager.pack(respAttrs,root);
				response.getOutputStream().write(mapper.writeValueAsBytes(entity));
			}
		}else {
			filterChain.doFilter(request, response);
		}
	}

	private boolean matchUri(String uri) {
		for(String uriPattern : ignorePaths) {
			if(PatternUtil.match(uriPattern, uri)) {
				return true;
			}
		}
		return false;
	}
}
