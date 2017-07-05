package com.kehsa.phonetablemodel;

import com.kehsa.phone.PhoneDB;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

/**
 * Created on 04.11.15.
 * @author kehsa
 */
public class PhoneCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final DefaultComboBoxModel<PhoneDB.PhoneBrand> comboBoxModel =
            new DefaultComboBoxModel<PhoneDB.PhoneBrand>(
                    PhoneDB.PhoneBrand.values()
            );

    private final JComboBox<PhoneDB.PhoneBrand> comboBox =
            new JComboBox<PhoneDB.PhoneBrand>(comboBoxModel);

    @Override
    public Component getTableCellEditorComponent(final JTable table,
                                                 final Object value,
                                                 final boolean b,
                                                 final int i, final int i1) {
        comboBoxModel.setSelectedItem(value);
        return comboBox;
    }

    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }
}
