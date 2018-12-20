import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel {
    Object[][] rowDate;
    Object[] columnData;
    private Object[] columnData1 = {"序号","姓名","投票"};
    private Object[] columnData2 = {"序号", "姓名","有效票","废票","弃票"};
    private StuFunc stuFunc;
    private int n;
    public Table(){}  //无参构造函数，用于调用方法

    // 0:checkbox;1:total
    public Table(int temp){
        this.columnData = getColumnData(temp);
        this.rowDate = getRowDate(temp);
    }

    private Class[] typeArray_1 = {int.class, String.class, Boolean.class};
    private Class[] typeArray_2 = {int.class, String.class, int.class, int.class, int.class};

    public Object[][] getRowDate(int n){
        if(n == 0){
            stuFunc = new StuFunc();
            rowDate = stuFunc.SelectStu("BOX");
        }
        else if(n == 1){
            stuFunc = new StuFunc();
            rowDate = stuFunc.SelectStu("");
        }
        return rowDate;
    }
    public Object[] getColumnData(int n){
        if(n==0){
            columnData = new Object[3];
            columnData = columnData1;
        }else if(n ==1){
            columnData = new Object[5];
            columnData = columnData2;
        }
        return columnData;
    }

    public void updateTable(Object[][] rowData, Object[] columns, int n) {
        this.n = n;
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

    // 使表格具有可编辑性
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
    // 返回每一列的数据类型
    public Class getColumnClass(int columnIndex) {
        if(n == 0)
            return typeArray_1[columnIndex];
        else
            return typeArray_2[columnIndex];
    }
    // 替换单元格的值
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        rowDate[rowIndex][columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
