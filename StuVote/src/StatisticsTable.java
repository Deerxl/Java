import javax.swing.*;

public class StatisticsTable extends JFrame {
    JFrame StatisticsUI;
    JTable table;
    StuFunc stuFunc;
    Object[][] rowData;
    Object[] columnData;

    public StatisticsTable(){
        StatisticsUI = new JFrame();
        table = new JTable();

        stuFunc = new StuFunc();

    }
}
