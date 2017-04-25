import static org.junit.Assert.*;

import org.junit.Test;
import org.mef.twixt.StringValue;
import org.mef.twixt.Value;
import org.mef.twixt.validate.Validator;
import org.mef.twixt.validate.ValContext;
import org.mef.twixt.validate.ValidationErrors;

public class BasicTests 
{
	public static class PhoneNum extends StringValue
	{
		private class MyValidator implements Validator
		{

			@Override
			public void validate(ValContext valctx, Value obj) 
			{
				StringValue val = (StringValue) obj;
				String s = val.get();
				if (s.length() != 8) //258-1833
				{
					valctx.addError("sdfdfs");
				}
			}
			
		}
		
		public PhoneNum(String val) 
		{
			super(val);
			this.setValidator(new MyValidator());
		}
	}

	@Test
	public void test() throws Exception 
	{
		String s = "258-9099";
		PhoneNum ph = new PhoneNum(s);
		assertEquals(s, ph.get());
		
		String s2 = ph.toString();
		assertEquals(s, s2);
		
		ValContext vtx = new ValContext();
		ph.validate(vtx);
		assertEquals(true, vtx.succeeded());
		
		ph.fromString("555-66");
		assertEquals("555-66", ph.get());
		ph.validate(vtx);
		assertEquals(false, vtx.succeeded());
	}

}
