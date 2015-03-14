
import static org.junit.Assert.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;
import org.mef.twixt.BooleanValue;
import org.mef.twixt.Converter;
import org.mef.twixt.DateValue;
import org.mef.twixt.DoubleValue;
import org.mef.twixt.IntegerValue;
import org.mef.twixt.ListValue;
import org.mef.twixt.LongSelectValue;
import org.mef.twixt.LongValue;
import org.mef.twixt.SelectValue;
import org.mef.twixt.StringValue;
import org.mef.twixt.Value;
import org.mef.twixt.validate.ValContext;
import org.mef.twixt.validate.ValidationErrors;
import base.BaseTest;


public class TwixtTests extends BaseTest
{
	
	public class CommaIntegerValue extends IntegerValue
	{
		private class Conv implements Converter
		{

			@Override
			public String print(Object obj) 
			{
				Integer n = get();
				String s = NumberFormat.getNumberInstance(Locale.US).format(n);
				return s;
			}

			@Override
			public Object parse(String s) 
			{
				s = s.replace(",", "");
				Integer n = Integer.parseInt(s);
				return n;
			}
			
		}
		public CommaIntegerValue()
		{
			this(0);
		}
		public CommaIntegerValue(Integer n)
		{
			super(n);
			setConverter(new Conv());
		}
		

	}
	
	@Test
	public void test() 
	{
		IntegerValue v = new IntegerValue();
		v.set(44);
		assertEquals(44, v.get());
		
		LongValue v2 = new LongValue();
		v2.set(456L);
		assertEquals(456L, v2.get());
		
		BooleanValue v3 = new BooleanValue();
		assertEquals(false, v3.get());
		v3.set(true);
		assertEquals(true, v3.get());
		
		StringValue v4 = new StringValue();
		assertEquals("", v4.get());
		v4.set("sdf");
		assertEquals("sdf", v4.get());
		
		DateValue v5 = new DateValue();
		assertNotNull(v5.get());
		
		Date dt = new Date();
		int yr = dt.getYear();
		v5.set(dt);
		assertEquals(yr, v5.get().getYear());
		
		DoubleValue v6 = new DoubleValue();
		v6.set(45.6);
		assertEquals(45.6, v6.get(), 0.001);
		
		List<Value> L = new ArrayList<Value>();
		L.add(v3);
		L.add(v4);
		ListValue v7 = new ListValue();
		assertEquals(0, v7.size());
		v7.set(L);
		assertEquals(2, v7.size());
	}
	
	@Test
	public void testDate() throws Exception
	{
		Date dt = new Date(115, 01, 21, 8, 30); //Thu Feb 21 08:30:00 EST 2015
		log(dt.toString());
		
		DateValue val = new DateValue(dt);
		String s = val.toString();
		assertEquals("2015-02-21", s);
		
		val.fromString("2014-12-25");
		s = val.toString();
		assertEquals("2014-12-25", s);
	}

	@Test
	public void testComma() throws Exception 
	{
		CommaIntegerValue v = new CommaIntegerValue(12345);
		assertEquals("12,345", v.toString());
		
		v.fromString("4,5678");
		assertEquals(45678, v.get());
	}
	
	@Test
	public void testSelect() throws Exception 
	{
		Map<String,String> map = new TreeMap<String,String>();
		map.put("1", "apple");
		map.put("2", "banana");
		map.put("3", "cherry");
		
		SelectValue v = new SelectValue("2", map);
		assertEquals("2", v.get());
		
		ValContext vtx = new ValContext();
		v.validate(vtx);
		assertEquals(true, vtx.succeeded());
		v.set("4");
		v.validate(vtx);
		assertEquals(false, vtx.succeeded());
		
		assertEquals(false, SelectValue.class.isAssignableFrom(StringValue.class));
		assertEquals(true, StringValue.class.isAssignableFrom(SelectValue.class)); //StringValue v = vselect;
	}
	@Test
	public void testLongSelect() throws Exception 
	{
		Map<Long,String> map = new TreeMap<Long,String>();
		map.put(1L, "apple");
		map.put(2L, "banana");
		map.put(3L, "cherry");
		
		LongSelectValue v = new LongSelectValue(2L, map);
		assertEquals(2L, v.get());
		
		ValContext vtx = new ValContext();
		v.validate(vtx);
		assertEquals(true, vtx.succeeded());
		v.set(4L);
		v.validate(vtx);
		assertEquals(false, vtx.succeeded());
	}
}
