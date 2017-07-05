package com.kehsa.phone;

/**
 * Created on 10.09.15.
 * @author kehsa
 * class contain base information about phone
 */
public class PhoneDB implements Cloneable {
    /**
     * enum brands producing smartphones.
     */
    public enum PhoneBrand {
        Samsung,
        LG,
        Apple,
        Lenovo,
        Microsoft,
        HTC,
        Sony,
        Nokia
    }
    /** fast integer for multiply (gen hash). */
    public static final int INT_31 = 31;
    /** count of bits in int. */
    public static final int BITS_IN_INT = 32;
    /** field is name of brand.*/
    private PhoneBrand brand;
    /** field is name of model.*/
    private String model;
    /** field contain cost.*/
    private double cost;
    /** field contain id of phone.*/
    private int id;

    /**
     * Getter for id field.
     * @return id
     */
    public int getId() {
        return id;
    }
/*
    /**
     * Setter for id field.
     * @param id new id
     */
/*
    public void setId(int id) {
        this.id = id;
    }//*/

    /**
     * constructor with parameters.
     * @param newBrand of new object
     * @param newModel of new object
     * @param newCost of new object
     */
    public PhoneDB(final int newId, final PhoneBrand newBrand,
                   final String newModel, final double newCost) {
        id = newId;
        setBrand(newBrand);
        setModel(newModel);
        setCost(newCost);
    }

    /**
     * getter for brand field.
     * @return brand
     */
    public final PhoneBrand getBrand() {
        return brand;
    }

    /**
     * setter for brand field.
     * @param newBrand of brand field
     */
    public final void setBrand(final PhoneBrand newBrand) {
        this.brand = newBrand;
    }

    /**
     * getter for model field.
     * @return String model
     */
    public final String getModel() {
        return model;
    }

    /**
     * setter for model field.
     * @param newModel of brand field
     */
    public final void setModel(final String newModel) {
        this.model = newModel;
    }

    /**
     * getter for cost field.
     * @return double cost
     */
    public final double getCost() {
        return cost;
    }

    /**
     * setter for cost field.
     * @param newCost of cost
     * @throws IllegalArgumentException if newCost < 0
     */
    public final void setCost(final double newCost)
            throws IllegalArgumentException {
        if (newCost < 0.0) {
            throw new IllegalArgumentException("newCost = "
                    + newCost + "; cost < 0");
        }
        this.cost = newCost;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneDB phone = (PhoneDB) o;
        return Double.compare(phone.cost, cost) == 0
                && Integer.compare(phone.id, id) == 0
                && brand.equals(phone.brand) && model.equals(phone.model);

    }

    @Override
    public final int hashCode() {
        int result;
        result = brand.hashCode();
        result = INT_31 * result + model.hashCode();
        long temp = Double.doubleToLongBits(cost);
        result = INT_31 * result + (int) (temp ^ (temp >>> BITS_IN_INT));
        result = INT_31 * result + id;
        return result;
    }

    @Override
    public final String toString() {
        return "phone{ "
        + "brand= '" + brand + '\''
        + ", model= '" + model + '\''
        +  ", cost= " + cost
        + ", id= " + id + " }";
    }

    @Override
    public final PhoneDB clone() {
        try {
            super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new PhoneDB(
                this.getId(),
                this.getBrand(),
                this.getModel(),
                this.getCost()
        );
    }
}
