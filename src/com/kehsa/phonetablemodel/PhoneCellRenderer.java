package com.kehsa.phonetablemodel;

import com.kehsa.phone.PhoneDB;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created on 04.11.15.
 * @author kehsa
 */
public class PhoneCellRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(final JTable table,
                                                   final Object value,
                                                   final boolean b, final boolean b1,
                                                   final int i, final int i1) {
        JComboBox comboBox = new JComboBox<PhoneDB.PhoneBrand>(PhoneDB.PhoneBrand.values());
        comboBox.setSelectedItem(table.getModel().getValueAt(i, i1));
        return comboBox;
    }
}
