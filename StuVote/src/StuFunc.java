import com.mysql.cj.xdevapi.SqlDataResult;

import java.sql.*;
import java.util.Vector;

public class StuFunc {
    VoteTable voteTable;
    int columnCount;
    int rowCount;
    int rowIndex = 0;
    Object[][] rowData;


    public Connection MyConnect(){
        Connection con = null;
        String user = "root";
        String password = "MZL_Alu819";
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/StuVote?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";

        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url,user,password);
            System.out.println("驱动程序已装载");
            return con;
        }catch (Exception ex){
            System.out.println("驱动程序装载失败");
            ex.printStackTrace();

            return null;
        }
    }

    public void AddStu(String name) {
        Connection con = null;
        String sql = "insert into stu values (null, '" + name + "', 0, 0, 0)";
        Statement statement = null;

        try {
            con = MyConnect();
            statement = con.createStatement();
            statement.execute(sql);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            try {
                statement.close();
                con.close();
                System.out.println("添加成功");
            } catch (SQLException ex) {
                System.out.println("添加失败");
                ex.printStackTrace();
            }
        }
    }

    public void DelStu(String id){
        Connection con = null;
        Statement statement = null;
        String sql = "delete from stu where id = " + id + ";";

        try{
            con = MyConnect();
            statement = con.createStatement();
            statement.execute(sql);
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                statement.close();
                con.close();
                System.out.println("删除成功");
            }catch (SQLException ex){
                System.out.println("删除失败");
                ex.printStackTrace();
            }
        }
    }

    public void AlterStu(int id, String temp){
        Connection con = null;
        Statement statement = null;
        String sql = null;
        String sql_name = "update stu set name = '" + temp + "' where id = " + id;
        String sql_valid = "update stu set validVote = validVote + 1 where id = " + id;
        String sql_bad = "update stu set badVote = badVote + 1 where id = " + id;
        String sql_discard = "update stu set discardVote = discardVote + 1 where id = " + id;

        if(temp.equals("validVote")){
            sql = sql_valid;
        }else if(temp.equals("badVote")){
            sql = sql_bad;
        }else if(temp.equals("discardVote")){
            sql = sql_discard;
        }else{
            sql = sql_name;
        }

        try {
            con = MyConnect();
            statement = con.createStatement();
            statement.execute(sql);
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                statement.close();
                con.close();
                System.out.println("修改成功");
            }catch (SQLException ex){
                System.out.println("修改失败");
                ex.printStackTrace();
            }

        }
    } //参数分别为 validVote, badVote, discardVote, 其他

    public Object[][] SelectStu(String temp){
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet;
        String sql_box = "select id, name from stu where id > 1;";
        String sql_statistics = "select *from stu order by  validVote desc;";
        String sql;
        int n;
        if(temp.equals("BOX")){
            sql = sql_box;
            n = 0;
        }
        else{
            sql = sql_statistics;
            n = 1;
        }

        try {
            con = MyConnect();
            statement = con.createStatement();
            resultSet =  statement.executeQuery(sql);

            resultSet.last();
            rowCount = resultSet.getRow();  //获取行数

            resultSet.beforeFirst();  //移至最前

            if(n == 0){
                rowData = new Object[rowCount][3]; //定义动态二维数组
            }else if(n == 1){
                rowData = new Object[rowCount][5];
            }

            while(resultSet.next()){
                rowData[rowIndex][0] = resultSet.getInt(1);
                rowData[rowIndex][1] = resultSet.getString(2);
                if(n == 0){
                    rowData[rowIndex][2] = false;
                }else{
                    rowData[rowIndex][2] = resultSet.getInt(3);
                    rowData[rowIndex][3] = resultSet.getInt(4);
                    rowData[rowIndex][4] = resultSet.getInt(5);
                }
                rowIndex++;
            }

        }catch (SQLException ex){
            ex.printStackTrace();

        }finally {
            try {
                statement.close();
                con.close();

                System.out.println("查询成功");
                return rowData;
            }catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("查询失败");
                return null;
            }
        }
    } // 当 参数为 BOX 时 返回 Object[][] id,name

    public void ClearStu(){
        Connection con =null;
        Statement statement = null;
        String sql ="truncate table stu;";
        try {
            con = MyConnect();
            statement = con.createStatement();
            statement.execute(sql);
        }catch (SQLException ex){
            ex.printStackTrace();
        }finally {
            try {
                statement.close();
                con.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }

        }
    }
}
