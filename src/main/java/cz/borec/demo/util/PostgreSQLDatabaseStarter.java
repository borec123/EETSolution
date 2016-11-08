package cz.borec.demo.util;

import java.io.IOException;

public class PostgreSQLDatabaseStarter {

	public static int startDatabase() throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec("./PostgreSQL/9.4/bin/pg_ctl start -D ./PostgreSQL/9.4/data");
		int status = p.waitFor();
		return status;
	}
}
