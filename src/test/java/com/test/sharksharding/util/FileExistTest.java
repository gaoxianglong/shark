package com.test.sharksharding.util;

import java.io.File;
import org.junit.Test;
import com.sharksharding.util.FileExist;
import junit.framework.Assert;

public class FileExistTest {
	public @Test void testeExists() {
		String path = System.getProperty("java.io.tmpdir") + File.separator + "test.xml";
		Assert.assertEquals(FileExist.exists(path), FileExist.exist(path));
		System.out.println(FileExist.exists(path));
		path = System.getProperty("java.io.tmpdir") + File.separator + "cavalier.xml";
		Assert.assertEquals(FileExist.exists(path), FileExist.exist(path));
		System.out.println(FileExist.exists(path));
	}
}