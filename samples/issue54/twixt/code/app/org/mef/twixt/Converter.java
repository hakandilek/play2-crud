package org.mef.twixt;


public interface Converter 
{
	String print(Object obj);
	Object parse(String s);
}
