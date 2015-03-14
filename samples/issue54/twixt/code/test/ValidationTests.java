import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.hibernate.validator.*;



public class ValidationTests 
{
	public class Carrot
	{
		public String a;
	}
	
	public static class MyVal implements Validator
	{

		@Override
		public boolean supports(Class<?> arg0) 
		{
			if (arg0 == Carrot.class)
			{
				return true;
			}
			return false;
		}

		@Override
		public void validate(Object arg0, Errors arg1) 
		{
			Carrot carrot = (Carrot) arg0;
			
			if (carrot.a.equals("none"))
			{
				arg1.reject("a", "none is not allowed");
			}
			
		}
		
	}
	@Test
	public void test() 
	{
		Carrot carrot = new Carrot();
		carrot.a = "none";
		BindException errors = new BindException(carrot, "address");	
		
		MyVal validator = new MyVal();
		ValidationUtils.invokeValidator(validator, carrot, errors);		
		
		assertEquals(1, errors.getErrorCount());
		
		for(ObjectError err : errors.getAllErrors())
		{
			log(err.toString());
		}
	}
	
	
	protected void log(String s)
	{
		System.out.println(s);
	}

}
