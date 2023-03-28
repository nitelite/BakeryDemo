package main.domain;

import lombok.Data;
import main.service.IDGenerator;

import java.time.LocalDate;
import java.util.List;

@Data
public class Recipe {
	String id;
	String name;
	LocalDate created;
	RecipeType recipeType;
	List<RecipeLine> contents;

	public Recipe(String name, RecipeType recipeType, List<RecipeLine> contents) {
		this.id = IDGenerator.generateId();
		this.created = LocalDate.now();
		this.name = name;
		this.recipeType = recipeType;
		this.contents = contents;
	}
}
