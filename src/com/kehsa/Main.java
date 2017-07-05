package com.kehsa;

import com.kehsa.phone.PhoneDB;
import com.kehsa.phonetablemodel.PhoneCellEditor;
import com.kehsa.phonetablemodel.PhoneCellRenderer;
import com.kehsa.phonetablemodel.PhoneTableModel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * main class
 * Created on 03.10.15.
 * @author kehsa
 */
public final class Main extends JFrame {
    /**
     * main function.
     * @param args program arguments
     */
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(
                    new javax.swing.plaf.nimbus.NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Main();
    }

    /** In pixel. */
    public static final int ROW_HEIGHT = 22;
    /** Container. */
    private PhoneTableModel tableModel = new PhoneTableModel();
    /** Title constant. */
    static final String TITLE = "Phone editor";
    /** JTable. */
    private final JTable table;

    /**
     * Main func.
     */
    public Main() {
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        table = new JTable(tableModel);
        table.setShowGrid(true);
        table.setDefaultRenderer(PhoneDB.PhoneBrand.class, new PhoneCellRenderer());
        table.setDefaultEditor(PhoneDB.PhoneBrand.class, new PhoneCellEditor());
        table.setRowHeight(ROW_HEIGHT);
        JScrollPane jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

        add(initRightPanel(), BorderLayout.EAST);

        setVisible(true);
    }

    /**
     * Initialize JPanel.
     * @return JPanel
     */
    private JPanel initRightPanel() {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        int btnNum = 0;
        c.gridx = btnNum++;
        c.fill = GridBagConstraints.HORIZONTAL;

        final JButton btnAdd = new JButton("Add new");
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                tableModel.getPhoneContainer().add(new PhoneDB(
                        1, PhoneDB.PhoneBrand.Samsung, "Default", 0.0
                ));
                table.updateUI();
            }
        });
        c.gridy = btnNum++;
        rightPanel.add(btnAdd, c);

        final JButton btnRemove = new JButton("Remove");
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                int selectedRowCount = table.getSelectedRowCount();
                if (selectedRowCount == 0) {
                    return;
                }
                if (selectedRowCount == 1) {
                    tableModel.getPhoneContainer().delete(
                            tableModel.getPhoneContainer().get(
                                    table.getSelectedRow())
                    );
                } else {
                    int[] select = table.getSelectedRows();
                    LinkedList<PhoneDB> removePhones = new LinkedList<PhoneDB>();
                    for (int x : select) {
                        removePhones.add(tableModel.getPhoneContainer().get(x));
                    }
                    for (PhoneDB ph : removePhones) {
                        tableModel.getPhoneContainer().delete(ph);
                    }
                    table.clearSelection();
                }
                table.updateUI();
            }
        });
        c.gridy = btnNum++;
        rightPanel.add(btnRemove, c);

        final JButton btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                loadDialog();
            }
        });
        c.gridy = btnNum++;
        rightPanel.add(btnLoad, c);

        final JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        c.gridy = btnNum;
        rightPanel.add(btnExit, c);

        return rightPanel;
    }

    /** Load dialog. */
    private void loadDialog() {
        int result = JOptionPane.showOptionDialog(this,
                "Select data base:", "DB select",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"SQLite", "PostgreSQL"}, null
        );
        tableModel.dbChoice(result);
        load();
    }
    /** Load. */
    private void load() {
        tableModel.getPhoneContainer().load();
        table.updateUI();
        table.setShowGrid(true);
    }
}
