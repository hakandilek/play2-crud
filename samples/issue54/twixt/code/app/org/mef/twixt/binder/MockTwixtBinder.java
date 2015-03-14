package org.mef.twixt.binder;

import java.util.Map;

import org.mef.twixt.ValueContainer;


public class MockTwixtBinder<T extends ValueContainer> extends TwixtBinder<T>
	{
		private Map<String, String> mockData;

		public MockTwixtBinder(Class<T> clazz, Map<String,String> anyData)
		{
			super(clazz);
			this.mockData = anyData;
		}
		
		@Override
		public boolean bind() 
		{
			return bindFromMap(mockData);
		}

	}