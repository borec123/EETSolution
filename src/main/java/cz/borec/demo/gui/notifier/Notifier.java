package cz.borec.demo.gui.notifier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.TriggerAdapter;

public class Notifier extends TriggerAdapter {

	public void fire(Connection conn, ResultSet oldRow, ResultSet newRow)
			throws SQLException {
		System.out.println("notification --------------------------------------------->");
	}

	public static void main(String[] args) throws SQLException, InterruptedException {
		System.out.println(Notifier.class.getName());
		final String url = "jdbc:h2:~/test;AUTO_SERVER=FALSE";
		/*final String url = "jdbc:h2:tcp://localhost:9092/~/test";*/
		Connection conn = DriverManager.getConnection(url, "sa", "");
		Statement stat = conn.createStatement();
		stat.execute("create trigger notifier before insert, update, delete, rollback on order_t call \"cz.borec.demo.gui.notifier.Notifier\"");
		Thread.sleep(10000);
	}

}
