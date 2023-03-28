package main.domain;

import lombok.Data;
import main.service.IDGenerator;

@Data
public class Ingredient {
	String id;
	String name;
	long stockLevel;

	public Ingredient(String name, long stockLevel) {
		this.id = IDGenerator.generateId();
		this.name = name;
		this.stockLevel = stockLevel;
	}

	public void reduceStockLevel(long reduceBy) {
		this.stockLevel -= reduceBy;
	}
}
