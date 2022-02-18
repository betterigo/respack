package io.github.betterigo.respack.config;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import io.github.betterigo.respack.config.settings.PackSettings;
import io.github.betterigo.respack.core.ErrorBody;

@EnableConfigurationProperties({ PackSettings.class })
@Configuration
@ConditionalOnProperty(name = "respack.filter.settings.enable", havingValue = "true", matchIfMissing = true)
public class RespackAutoConfiguration {

	@RestController
	@AutoConfigureBefore({ ErrorMvcAutoConfiguration.class })
	@ConditionalOnMissingBean(ErrorController.class)
	class InnerJsonTypeErrorController implements ErrorController {

		private static final String PATH = "/error";
		private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
		@Autowired
		private ErrorAttributes errorAttributes;

		@Override
		public String getErrorPath() {
			return PATH;
		}

		@RequestMapping(path = PATH, produces = MediaType.APPLICATION_JSON)
		public ErrorBody sendError(HttpServletRequest req, HttpServletResponse res) {
			WebRequest webRequest = new ServletWebRequest(req);
			Throwable e = errorAttributes.getError(webRequest);
			LOGGER.error("", e);
			Map<String, Object> errorAttrs = errorAttributes.getErrorAttributes(webRequest, true);
			ErrorBody errorBody = new ErrorBody();
			errorBody.setMessage((String) errorAttrs.get("message"));
			if (e instanceof WebApplicationException) {
				errorBody.setStatus(((WebApplicationException) e).getResponse().getStatus());
				res.setStatus(((WebApplicationException) e).getResponse().getStatus());
			} else {
				errorBody.setStatus((Integer) errorAttrs.get("status"));
			}
			errorBody.setPath((String) errorAttrs.get("path"));
			errorBody.setTimeStamp((Date) errorAttrs.get("timestamp"));
			res.setHeader("Access-Control-Allow-Origin", "*"); // 不再spring mvc中，需要单独加上跨域
			return errorBody;

		}
	}
}
