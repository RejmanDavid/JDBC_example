import java.sql.*;

public class Main {
    public static void main(String[] args) {

        String name = "Jeremy";
        int age = 22;
        String cls = "PRO3";

        String selectQuery = "SELECT * FROM student WHERE name = ?";

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection con = DriverManager.getConnection("jdbc:derby:exampleDB;create=true");

            Statement st = con.createStatement();

            //st.addBatch("drop table student");
            //st.addBatch("delete from student");
            st.addBatch("create table student (id int not null generated always as " +
                    "identity constraint student_PK primary key, " +
                    "name varchar(20),age int,class varchar(5))");
            st.executeBatch();

            st = con.createStatement();
            st.addBatch("insert into student (name,age,class) values" +
                    "('" + name + "'," + age + ",'" + cls + "')");
            st.executeBatch();

            //ResultSet res = st.executeQuery("select * from student");
            PreparedStatement ps = con.prepareCall(selectQuery);
            ps.setString(1,"Jeremy");

            ResultSet res = ps.executeQuery();
            while(res.next()){
                System.out.print(res.getString("id")+" ");
                System.out.print(res.getString("name")+" ");
                System.out.print(res.getString("age")+" ");
                System.out.println(res.getString("class")+" ");
            }

            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
