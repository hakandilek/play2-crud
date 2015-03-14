package base;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;



public class BaseTest 
{
	protected void createContext()
	{
	}
	
	protected void log(String s)
	{
		System.out.println(s);
	}
	protected String getCurrentDirectory()
	{
		File f = new File(".");
		return f.getAbsolutePath();
	}

	protected String getTestFile(String filepath)
	{
		String path = this.getCurrentDirectory();
		path = pathCombine(path, "test\\tools\\testfiles");
		path = pathCombine(path, filepath);
		return path;
	}
	protected String getUnitTestDir(String filepath)
	{
		String path = this.getCurrentDirectory();
		path = pathCombine(path, "test\\unittests");
		if (filepath != null)
		{
			path = pathCombine(path, filepath);
		}
		return path;
	}
	protected String getCurrentDir(String filepath)
	{
		String path = this.getCurrentDirectory();
		if (filepath != null)
		{
			path = pathCombine(path, filepath);
		}
		return path;
	}

	
	
	protected String pathCombine(String path1, String path2)
	{
		if (! path1.endsWith("\\"))
		{
			path1 += "\\";
		}
		String path = path1 + path2;
		return path;
	}
	
}
