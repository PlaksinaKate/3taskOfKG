package com.company.frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import com.intellij.uiDesigner.core.GridConstraints;
//import com.intellij.uiDesigner.core.GridLayoutManager;
//import com.intellij.uiDesigner.core.Spacer;
import com.company.DrawPanel;
import com.company.figure.RoundedPolygon;
import com.company.frame.utils.JTableUtils;
import com.company.frame.utils.SwingUtils;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jgoodies.forms.layout.CellConstraints;

import java.awt.*;

import static com.company.frame.utils.JTableUtils.*;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInputX;
    private JScrollPane scrollPaneInputX;
    private JScrollPane scrollPaneInputY;
    private JTable tableInputY;
    private JPanel panelInputN;
    private JLabel nJLabel;
    private JLabel yJLabel;
    private JLabel xJLabel;
    private JSpinner spinnerN;
    private JPanel panelForButton;
    private JButton drawButton;

    public FrameMain() {
        this.setTitle("Нарисовать Скругленный многоугольник");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        initJTableForArray(tableInputX, 40, true, true, true, true);
        initJTableForArray(tableInputY, 40, true, true, true, true);

        JTableUtils.writeArrayToJTable(tableInputX, new int[]{-1, 2, 5});
        JTableUtils.writeArrayToJTable(tableInputY, new int[]{-2, 2, 1});


        //tableOutput.setEnabled(false);
        tableInputX.setRowHeight(25);
        tableInputY.setRowHeight(25);


        this.pack();
        this.setSize(400, 500);
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
//                    int[] x = readIntArrayFromJTable(tableInputX);
//                    int[] y = readIntArrayFromJTable(tableInputY);
//                    int n = (int) spinnerN.getValue();
//
//                    RoundedPolygon.readArrayFromFrame(x);
//                    RoundedPolygon.readArrayFromFrame(y);
//                    RoundedPolygon.readNumberFromFrame(n);
//                    DrawPanel dr = new DrawPanel();

                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

    }

    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), 10, 10));
        xJLabel = new JLabel();
        xJLabel.setText("Введите x");
        CellConstraints cc = new CellConstraints();
        panelMain.add(xJLabel, cc.xy(1, 1));
        scrollPaneInputX = new JScrollPane();
        scrollPaneInputX.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPaneInputX, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        tableInputX = new JTable();
        scrollPaneInputX.setViewportView(tableInputX);
        yJLabel = new JLabel();
        yJLabel.setText("Введите y");
        CellConstraints c = new CellConstraints();
        panelMain.add(yJLabel, c.xy(1, 1));
        scrollPaneInputY = new JScrollPane();
        scrollPaneInputY.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPaneInputY, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 200), null, 0, false));
        tableInputY = new JTable();
        scrollPaneInputX.setViewportView(tableInputY);
        panelInputN = new JPanel();
        panelInputN.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelInputN, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        nJLabel = new JLabel();
        nJLabel.setText("Введите n");
        CellConstraints c1 = new CellConstraints();
        panelInputN.add(xJLabel, c1.xy(1, 1));
        spinnerN = new JSpinner();
        panelInputN.add(spinnerN, c1.xy(3, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
        panelForButton = new JPanel();
        panelForButton.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panelForButton, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        drawButton = new JButton();
        drawButton.setText("Нарисовать");
        panelForButton.add(drawButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
