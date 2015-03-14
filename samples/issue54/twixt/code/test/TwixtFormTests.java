import static org.junit.Assert.*;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.mef.twixt.StringValue;
import org.mef.twixt.binder.MockTwixtBinder;
import org.mef.twixt.binder.TwixtBinder;
import org.mef.twixt.binder.TwixtForm;

import base.BaseTest;


public class TwixtFormTests extends BaseTest
{
	public static class CarTwixt extends TwixtForm
	{
		public StringValue a;
		public StringValue b;
		
		public CarTwixt()
		{
			a = new StringValue();
			b = new StringValue();
		}
	}

	@Test
	public void test() 
	{
		CarTwixt twixt = new CarTwixt();
		assertEquals("", twixt.a.get());
		
		MockTwixtBinder<CarTwixt> binder = new MockTwixtBinder<CarTwixt>(CarTwixt.class, buildMap());
		
		boolean b = binder.bind();
		assertTrue(b);
		twixt = binder.get();
		
		assertEquals("abc", twixt.a.get());
		assertEquals("def", twixt.b.get());
	}

	
	private Map<String,String> buildMap()
	{
		Map<String,String> map = new TreeMap<String,String>();
		map.put("a", "abc");
		map.put("b", "def");
		
		return map;
	}
}
