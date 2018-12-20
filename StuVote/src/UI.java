import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import java.awt.*;

public class UI extends JFrame{
    JFrame frame;
    JButton addBtn, delBtn, alterBtn, statisticsBtn, saveBtn, helpBtn,clearBtn;
    JPanel btnPanel, savePanel;
    JScrollPane scrollPane;
    JTable stuTable;
    StuFunc stuFunc;
    int stuNum =0;   // 总人数
    int box[][];
    Table table;
    JCheckBox checkBox;

    public static void main(String[] args){
        UI voteUI = new UI();
    }

    public UI(){
        frame = new JFrame("投票管理系统");

        addBtn = new JButton("添加候选人");
        addBtn.addActionListener(new btnListener());
        delBtn = new JButton("删除候选人");
        delBtn.addActionListener(new btnListener());
        alterBtn = new JButton("修改候选人");
        alterBtn.addActionListener(new btnListener());
        statisticsBtn = new JButton("统计结果");
        statisticsBtn.addActionListener(new btnListener());
        clearBtn = new JButton("清除数据");
        clearBtn.addActionListener(new btnListener());
        saveBtn = new JButton("投票（投票前请先查点击“帮助”按钮查看提示）");
        saveBtn.addActionListener(new btnListener());
        helpBtn = new JButton("帮助");
        helpBtn.addActionListener(new btnListener());

        table = new Table(0);
        stuTable = new JTable(table.rowDate,table.columnData);
        RefreshTable();

        checkBox = new JCheckBox();
        TableColumn tableColumn = stuTable.getColumnModel().getColumn(2);
        tableColumn.setCellEditor(new DefaultCellEditor(checkBox));

        btnPanel = new JPanel(new GridLayout(1,6));
        scrollPane = new JScrollPane(stuTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        savePanel = new JPanel(new GridLayout(1,1));

        btnPanel.add(addBtn);
        btnPanel.add(delBtn);
        btnPanel.add(alterBtn);
        btnPanel.add(statisticsBtn);
        btnPanel.add(clearBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(helpBtn);

        scrollPane.add(stuTable);
        scrollPane.setViewportView(stuTable);

        savePanel.add(saveBtn);

        this.add(btnPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(savePanel,BorderLayout.SOUTH);

        this.setSize(800,600);
        this.setVisible(true);
    }

    class btnListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == addBtn){
                EditUI addUI = new EditUI("ADD");
                RefreshTable();
            }else if(e.getSource() == delBtn){
                EditUI delUI = new EditUI("DELETE");
                RefreshTable();
            }else if(e.getSource()== alterBtn){
                EditUI alterUI = new EditUI("ALTER");
                RefreshTable();
            }else if(e.getSource() == statisticsBtn){
                //tableUI = new TableUI();
                RefreshTotal();
            }else if(e.getSource() == clearBtn){
                stuFunc = new StuFunc();
                stuFunc.ClearStu();
                stuFunc.AddStu("total");
                RefreshTable();
            } else if(e.getSource() == saveBtn){
                stuNum = stuTable.getRowCount();    //总人数
                box = new int[stuNum][2];
                for(int i = 0;i<box.length;i++){
                    box[i][0] = Integer.parseInt(stuTable.getValueAt(i,0).toString());
                    int n = 0;
                    if(stuTable.getValueAt(i,2).toString().equals("true"))
                        n =1;
                    box[i][1] = n;
                }
                SetResults(box);
                JOptionPane.showMessageDialog(null,"投票成功");

                RefreshTable();
            }else if(e.getSource()== helpBtn){
                String helpText = "1.每人必须投3票，少者作为弃票，多者作为废票；\n" +
                        "2.添加候选人请在候选人之间用空格分隔开；\n" +
                        "3.只能修改候选人的名字，不得修改得票数；\n" +
                        "4.点击统计按钮可查看投票情况，其中第一条是统计，validVote:有效票；badVote:废票；discardVote:弃票；\n" +
                        "5.在每次操作之后，表格会进行刷新，统计结果的表头分别为：序号，姓名，有效票，废票，弃票；";
                JOptionPane.showMessageDialog(null, helpText);
            }
        }
    }

    //统计结果，二维数组，id，是否投票，0：未投；1，已投
    private void SetResults(int[][] temp){
        int count = 0;
        int i = 0;
        int k = 0;

        while(i<temp.length){
            if(temp[i][1] == 1){
                count++;
            }
            i++;
        }
        int[] data = new int[count];
        int m = 0;
        while(k<temp.length){
            if(temp[k][1] == 1){
                data[m] = temp[k][0];
                m++;
            }
            k++;
        }
        if(count == 3){
            stuFunc = new StuFunc();
            stuFunc.AlterStu(1,"validVote");
            for(int j =0;j< data.length;j++){
                stuFunc.AlterStu(data[j],"validVote");
            }
        }else if(count<3){
            stuFunc = new StuFunc();
            stuFunc.AlterStu(1,"discardVote");
            for(int j =0;j< data.length;j++){
                stuFunc.AlterStu(data[j],"discardVote");
            }
        }else if(count>3){
            stuFunc = new StuFunc();
            stuFunc.AlterStu(1,"badVote");
            for(int j =0;j< data.length;j++){
                stuFunc.AlterStu(data[j],"badVote");
            }
        }
    }

    private void RefreshTable(){
        stuFunc = new StuFunc();
        Object[][] data = stuFunc.SelectStu("BOX");
        table.updateTable(data,table.getColumnData(0),0);
        stuTable.setModel(table);
    }

    private void RefreshTotal(){
        stuFunc = new StuFunc();
        Object[][] data = stuFunc.SelectStu("");
        table.updateTable(data,table.getColumnData(1),1);
        stuTable.setModel(table);
    }
}
