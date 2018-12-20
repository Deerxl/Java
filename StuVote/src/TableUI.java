import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.adapter.JavaBeanLongPropertyBuilder;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Vector;

class VoteTable extends AbstractTableModel {
    Object[][] rowDate;
    Object[] columnData = {"序号","姓名","有效票","废票","弃票"};
    StuFunc stuFunc;

    public VoteTable(){}  //无参构造函数，用于调用方法

    public VoteTable(String temp){
        this.rowDate = getRowDate();
    }

    public Object[][] getRowDate(){
        stuFunc = new StuFunc();
        rowDate = stuFunc.SelectStu("");
        return rowDate;
    }

    public void updateTable(Object[][] rowData, Object[] columns) {
        this.rowDate = rowData;
        this.columnData = columns;
        this.fireTableDataChanged();
    }
    public int getRowCount(){
        return this.rowDate.length;
    }
    public int getColumnCount(){
        return this.columnData.length;
    }
    public Object getValueAt(int row, int column){
        return this.rowDate[row][column];
    }
}


public class TableUI extends JFrame{
    JFrame dialog;
    JLabel label1, label2, label3, label4;
    JTable table;
    VoteTable voteTable;
    JPanel panel1, panel3,panel4, panel5;
    JPanel panel2;

    StuFunc stuFunc;


    public TableUI(){
        dialog = new JFrame();
        dialog.setSize(720,600);

        label1 =new JLabel("以下是查询结果");
        label2 = new JLabel("注：第一行是统计。");
        label3 = new JLabel("表格在右边");
        label4 = new JLabel("表格在左边");

        panel1 = new JPanel(new GridLayout(1,7));
        panel1.add(label1);

        panel3 = new JPanel(new GridLayout(1,7));
        panel3.add(label2);

        panel4 = new JPanel(new GridLayout(10,1));
        panel4.add(label3);

        panel5 = new JPanel(new GridLayout(10,1));
        panel5.add(label4);

        voteTable = new VoteTable("");
        table = new JTable(voteTable.getRowDate(), voteTable.columnData);
        //table = new JTable();
        table.setModel(voteTable);

        panel2 = new JPanel();
        //panel2.setViewportView(table);
        panel2.add(table);

        dialog.add(panel1, BorderLayout.NORTH);
        dialog.add(panel2, BorderLayout.CENTER);
        dialog.add(panel3,BorderLayout.SOUTH);
        dialog.add(panel4, BorderLayout.WEST);
        dialog.add(panel5,BorderLayout.EAST);

        //panel1.setPreferredSize(new Dimension(60,30));
        //dialog.setSize(480,360);
        //dialog.setModal(true);
        dialog.setVisible(true);
    }
}


