package hr.java.project.data;

import hr.java.project.ProjectApplication;
import hr.java.project.entiteti.Stats;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//OVO NEZ STO JE MOGLO BI BITI JE LI KUPLJENO ILI JE U KOSARICI ILI JE LI PLACENO NEZZ
public interface StatsData {

    static Stats getCurrentStats() {
        Stats statsToReturn = null;
        try (Connection conn = DataBase.connectingToDatabase()) {

            Statement sqlStatement = conn.createStatement();
            ResultSet proceduresResultSet = sqlStatement.executeQuery(
                    "SELECT * FROM KUPNJE WHERE ID=0"
            );

            while (proceduresResultSet.next()) {
                statsToReturn = new Stats(proceduresResultSet.getInt("id"), proceduresResultSet.getInt("kupci"), proceduresResultSet.getDouble("cijena"), proceduresResultSet.getInt("user"), proceduresResultSet.getInt("racuni"));
            }

            return statsToReturn;

        } catch (SQLException | IOException e) {
            ProjectApplication.logger.error(e.getMessage(), e);
        }
        return statsToReturn;
    }

}
