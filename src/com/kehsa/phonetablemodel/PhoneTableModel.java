package com.kehsa.phonetablemodel;

import com.kehsa.phone.PhoneDB;
import com.kehsa.phonecontainer.AbstractPhoneSQLContainer;
import com.kehsa.phonecontainer.PhonePostgreSqlContainer;
import com.kehsa.phonecontainer.PhoneSQLiteContainer;

import javax.swing.table.AbstractTableModel;

/**
 * Created on 14.10.15.
 * @author kehsa
 */
public class PhoneTableModel extends AbstractTableModel {
    /** phone container.*/
    private AbstractPhoneSQLContainer phoneSQLContainer;
    /** column count constant.*/
    static final int COLUMN_COUNT = 3;
    /** Column names. */
    static final String[] COL_NAMES = {
        "Brand",
        "Model",
        "Cost"
    };

    /**
     * Constructor.
     */
    public PhoneTableModel() {
        dbChoice(0);
    }

    /**
     * SQL container init.
     * @param db 1=PostgreSQL, default(0)=SQLite
     */
    public void dbChoice(int db) {
        switch (db) {
            case 1:
                phoneSQLContainer = new PhonePostgreSqlContainer();
                break;
            default:
                phoneSQLContainer = new PhoneSQLiteContainer();
        }
    }

    /**
     * getter for container.
     * @return PhoneFileContainer
     */
    public AbstractPhoneSQLContainer getPhoneContainer() {
        return phoneSQLContainer;
    }

    @Override
    public int getRowCount() {
        return phoneSQLContainer.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public Object getValueAt(final int row, final int col) {
        PhoneDB tempPhone = phoneSQLContainer.get(row);
        Object obj;
        switch (col) {
            case 0:
                obj = tempPhone.getBrand();
                break;
            case 1:
                obj = tempPhone.getModel();
                break;
            case 2:
                obj = tempPhone.getCost();
                break;
            default:
                obj = new Object();
        }
        return obj;
    }

    @Override
    public String getColumnName(final int col) {
        return COL_NAMES[col];
    }

    @Override
    public boolean isCellEditable(final int row, final int col) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(final int col) {
        return getValueAt(0, col).getClass();
    }

    @Override
    public void setValueAt(final Object value, final int row, final int col) {
        PhoneDB phone = phoneSQLContainer.get(row);
        PhoneDB newPhone = phone.clone();
        switch (col) {
            case 0:
                newPhone.setBrand((PhoneDB.PhoneBrand) value);
                break;
            case 1:
                newPhone.setModel((String) value);
                break;
            case 2:
                newPhone.setCost((Double) value);
            default:
                break;
        }
        phoneSQLContainer.set(row, newPhone);
    }
}
