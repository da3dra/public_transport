package ua.provectus.public_transport.aop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.rollbar.Rollbar;

@ControllerAdvice
class RollbarExceptionHandler {
	private final Rollbar rollbar= new Rollbar("4d1a425da29e48408e2d7354ae41c8fe", "develop");

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;

		rollbar.log(e);

		throw e;
	}
	
	@ExceptionHandler(IOException.class)
	public ModelAndView handleIOException(HttpServletRequest request, Exception ex){
		
		rollbar.log(ex);
		
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    modelAndView.setViewName("error");
	    return modelAndView;
	}	
}