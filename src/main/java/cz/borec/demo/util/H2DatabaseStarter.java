package cz.borec.demo.util;

import java.io.File;
import java.io.IOException;

public class H2DatabaseStarter {

	public static int startDatabase() throws IOException, InterruptedException {
		//TODO do it platform independent (start server using java code).
		//Process p = Runtime.getRuntime().exec("dir");
		Process p = Runtime.getRuntime().exec("cmd /c start h2_server.bat", null, new File("./h2/bin")); // rem h2/bin/h2_server.bat");
		int status = p.waitFor();
		return status;
	}
}
