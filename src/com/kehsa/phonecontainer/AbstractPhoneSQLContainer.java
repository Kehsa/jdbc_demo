package com.kehsa.phonecontainer;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.kehsa.icontainer.IContainer;
import com.kehsa.phone.PhoneDB;
import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created on 28.11.2015.
 * @author kehsa
 * class SQL conteiner for PhoneDB objects
 */
public abstract class AbstractPhoneSQLContainer implements Iterable<PhoneDB>, IContainer<PhoneDB> {
    /** SQL db statement. */
    protected static Statement db;
    /** SQL result set. */
    protected static ResultSet resultSet;
    /** Brands id - brand enum Map */
    protected static BiMap<Integer, PhoneDB.PhoneBrand> brandMap = new HashBiMap<>();
    /** ArrayList collection for PhoneDB objects. */
    private ArrayList<PhoneDB> phoneArrayList = new ArrayList<PhoneDB>();
    /** SQL add prepare statement. */
    protected static PreparedStatement dbAddNewPhone;
    /** SQL get prepare statement. */
    protected static PreparedStatement dbGetIdNewPhone;

    /**
     * Set new phone list.
     * @param ls new phone list
     */
    public void setPhoneList(ArrayList<PhoneDB> ls) {
        if (ls != null) {
            phoneArrayList = ls;
        }
    }
    /** Specific DB initialization */
    abstract void initDb();
    /** Constructor. */
    public AbstractPhoneSQLContainer() {
        try {
            initDb();
            resultSet = db.executeQuery("SELECT COUNT(*) FROM brands");
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                PhoneDB.PhoneBrand[] brandLs = PhoneDB.PhoneBrand.values();
                for (PhoneDB.PhoneBrand brand : brandLs) {
                    db.executeUpdate("INSERT INTO brands (brandname) VALUES ('"
                            + brand.toString() + "');");
                }
            }
            resultSet = db.executeQuery("SELECT * FROM brands");
            while (resultSet.next()) {
                String tmpStr = resultSet.getString(2);
                PhoneDB.PhoneBrand tmpBrand = PhoneDB.PhoneBrand.Nokia;
                for (PhoneDB.PhoneBrand br : PhoneDB.PhoneBrand.values()) {
                    if (tmpStr.equals(br.toString())) {
                        tmpBrand = br;
                    }
                }
                brandMap.put(resultSet.getInt(1), tmpBrand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function load phone list from database.
     */
    public void load() {
        ArrayList<PhoneDB> tempList = new ArrayList<>();
        PhoneDB temPhone;
        try {
            resultSet = db.executeQuery("SELECT * FROM phones LEFT JOIN brands USING (brand);");
            while (resultSet.next()) {
                PhoneDB.PhoneBrand tmpBrand = brandMap.get(resultSet.getInt("brand"));
                temPhone = new PhoneDB(
                        resultSet.getInt("id"), tmpBrand,
                        resultSet.getString("model"),
                        resultSet.getDouble("cost")
                );
                tempList.add(temPhone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.phoneArrayList = tempList;
    }

    @Override
    public final PhoneDB get(final int i) {
        int maxIndex = phoneArrayList.size() - 1;
        if (i > maxIndex && i >= 0) {
            throw new IndexOutOfBoundsException("Index = "
            + i + ", Size = " + (maxIndex + 1));
        }
        return phoneArrayList.get(i);
    }

    @Override
    public void set(final int i, final PhoneDB newPhone) {
        PhoneDB oldPhone = phoneArrayList.get(i);
        if (oldPhone.getId() != newPhone.getId()) {
            System.out.println(oldPhone.getId()+"\t"+newPhone.getId());
        }
        StringBuilder set = new StringBuilder("SET ");
        if (!oldPhone.getBrand().equals(newPhone.getBrand())) {
            set.append("brand = '");
            set.append(brandMap.inverse().get(newPhone.getBrand()));
            set.append("' ");
        }
        if (!oldPhone.getModel().equals(newPhone.getModel())) {
            if (set.length() > 4) {
                set.append(", ");
            }
            set.append("model = '");
            set.append(newPhone.getModel());
            set.append("' ");
        }
        if (!(oldPhone.getCost() == newPhone.getCost())) {
            if (set.length() > 4) {
                set.append(", ");
            }
            set.append("cost = '");
            set.append(newPhone.getCost());
            set.append("' ");
        }
        try {
            db.executeUpdate("UPDATE phones " + set + " WHERE id='"
                        + oldPhone.getId() + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        phoneArrayList.set(i, newPhone);
    }

    @Override
    public final int size() {
        return phoneArrayList.size();
    }

    @Override
    public void delete(final PhoneDB obj) {
        try {
            db.executeUpdate("DELETE FROM phones WHERE id="
                    + obj.getId() + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        phoneArrayList.remove(obj);
    }

    @Override
    public final boolean isEmpty() {
        return phoneArrayList.isEmpty();
    }

    @Override
    public final Iterator<PhoneDB> iterator() {
        return phoneArrayList.iterator();
    }

    @Override
    public void add(@Nullable final PhoneDB phone) {
        PhoneDB tempPhone;
        try {
            dbAddNewPhone.setInt(1, brandMap.inverse().get(phone.getBrand()));
            dbAddNewPhone.setString(2, phone.getModel());
            dbAddNewPhone.setDouble(3, phone.getCost());
            dbAddNewPhone.executeUpdate();
            dbGetIdNewPhone.setInt(1, brandMap.inverse().get(phone.getBrand()));
            dbGetIdNewPhone.setString(2, phone.getModel());
            dbGetIdNewPhone.setDouble(3, phone.getCost());
            resultSet = dbGetIdNewPhone.executeQuery();
            int tmp = 0;
            while (resultSet.next()) {
                tmp = resultSet.getInt("id");
            }
            tempPhone = new PhoneDB(
                    tmp,
                    phone.getBrand(),
                    phone.getModel(),
                    phone.getCost()
            );
            phoneArrayList.add(tempPhone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        phoneArrayList.clear();
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PhoneDB Container{\n");
        for (PhoneDB anArrayList : phoneArrayList) {
            sb.append(anArrayList.toString());
            sb.append('\n');
        }
        sb.append('}');
        return sb.toString();
    }
}
