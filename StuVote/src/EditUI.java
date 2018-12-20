import javafx.beans.binding.ObjectBinding;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class EditUI extends JDialog{
    JDialog editUI;
    JLabel label;
    JButton saveBtn;
    JPanel panel1, panel3;
    JTextPane panel2;
    int choice;
    StringTokenizer stringTokenizer;
    int stuCounts;
    String[] names;
    StuFunc stuFunc;


    // 参数分为 ADD,DELETE, ALTER
    public EditUI(@org.jetbrains.annotations.NotNull String temp){
        editUI = new JDialog();

        if(temp.equals("ADD")){
            label = new JLabel("请输入候选人名字，不同候选人之间用空格分隔开");
            choice = 1;
        }else if(temp.equals("DELETE")){
            label = new JLabel("请依次输入要删除的候选人的序号，用空格分开");
            choice = 2;
        }else if(temp.equals("ALTER")){
            label = new JLabel("请依次输入要修改的候选人的序号和修改之后的名字，用空格分开");
            choice = 3;
        }

        saveBtn = new JButton("保存");
        saveBtn.addActionListener(new btnListener());

        panel1 = new JPanel();
        panel2 = new JTextPane();
        panel3 = new JPanel();

        panel1.add(label);
        panel3.add(saveBtn);

        editUI.add(panel1, BorderLayout.NORTH);
        editUI.add(panel2,BorderLayout.CENTER);
        editUI.add(panel3, BorderLayout.SOUTH);

        editUI.setSize(300,200);
        editUI.setModal(true);
        editUI.setVisible(true);
    }

    class btnListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == saveBtn) {
                names = getStu();
                switch (choice) {
                    case 1:  //add
                        for (int i = 0; i < stuCounts; i++) {
                            stuFunc = new StuFunc();
                            stuFunc.AddStu(names[i]);
                        }
                        JOptionPane.showMessageDialog(null,"添加成功");
                        break;
                    case 2:   //delete
                        for(int i= 0; i<stuCounts;i++){
                            stuFunc = new StuFunc();
                            stuFunc.DelStu(names[i]);
                        }
                        JOptionPane.showMessageDialog(null,"删除成功");
                        break;
                    case 3:    //alter
                        for(int i = 0; i<stuCounts;i += 2){
                            stuFunc = new StuFunc();
                            stuFunc.AlterStu(Integer.parseInt(names[i]) ,names[i+1]);
                        }
                        break;
                }
                editUI.dispose();

            }
        }
    }

    public String[] getStu(){
        String[] stu = null;
        int i = 0;
        try {
            String m = panel2.getText();
            stringTokenizer = new StringTokenizer(panel2.getText());
            stuCounts = stringTokenizer.countTokens();
            stu = new String[stuCounts];
            while(stringTokenizer.hasMoreTokens()){
                stu[i] = stringTokenizer.nextToken();
                i++;
            }
            return stu;
        }catch (Exception ex){
            JOptionPane.showMessageDialog(null,"请正确输入学生信息");
            return null;
        }
    }

    
}
