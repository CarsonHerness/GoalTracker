package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Reward {

    private StringProperty name;
    public void setName(String value) { 
    	nameProperty().set(value);
    }
    public String getName() { 
    	return nameProperty().get(); 
    }
    public StringProperty nameProperty() { 
        if (name == null) {
        	name = new SimpleStringProperty(this, "name");
        }
        return name; 
    }
    
    private DoubleProperty cost;
    public void setCost(double value) {
    	costProperty().set(value);
    }
    public double getCost() {
    	return costProperty().get();
    }
    public DoubleProperty costProperty() {
    	if (cost == null) {
    		cost = new SimpleDoubleProperty(this, "cost");
    	}
    	return cost;
    }
    
    Reward(String name, Double cost) {
    	this.name = new SimpleStringProperty(name);
    	this.cost = new SimpleDoubleProperty(cost);
    }
	
}
