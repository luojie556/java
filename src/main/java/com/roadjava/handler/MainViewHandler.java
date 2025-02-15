package com.roadjava.handler;

import com.roadjava.service.StudentService;
import com.roadjava.service.impl.StudentServiceImpl;
import com.roadjava.student.view.AddStudentView;
import com.roadjava.student.view.MainView;
import com.roadjava.student.view.UpdateStudentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author zhaodaowen
 * @see <a href="http://www.roadjava.com">乐之者java</a>
 */
public class MainViewHandler implements ActionListener {

    private MainView mainView;
    public MainViewHandler(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource();
        String text = jButton.getText();
        if ("增加".equals(text)) {
            new AddStudentView(mainView);
        }else if ("修改".equals(text)) {
            int[] selectedStudentIds = mainView.getSelectedStudentIds();
            if (selectedStudentIds.length != 1) {
                JOptionPane.showMessageDialog(mainView,"一次只能修改一行!");
                return;
            }
            new UpdateStudentView(mainView,selectedStudentIds[0]);
        }else if ("删除".equals(text)) {
            int[] selectedStudentIds = mainView.getSelectedStudentIds();
            if (selectedStudentIds.length == 0) {
                JOptionPane.showMessageDialog(mainView,"请选择要删除的行!");
                return;
            }
            int option = JOptionPane.showConfirmDialog(mainView, "你确认要删除选择的"
                            + selectedStudentIds.length + "行吗?", "确认删除",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) { // 确认
                // 执行删除
                StudentService studentService = new StudentServiceImpl();
                boolean deleteResult = studentService.delete(selectedStudentIds);
                if (deleteResult) {
                    // 重新加载表格查到最新数据
                    mainView.reloadTable();
                } else {
                    JOptionPane.showMessageDialog(mainView,"删除失败");
                }
            }
        }else if ("查询".equals(text)) {
            mainView.setPageNow(1);
            mainView.reloadTable();
        }else if ("上一页".equals(text)) {
            mainView.setPageNow(mainView.getPageNow() - 1);
            mainView.reloadTable();
        }else if ("下一页".equals(text)) {
            mainView.setPageNow(mainView.getPageNow() + 1);
            mainView.reloadTable();
        }
    }
}
