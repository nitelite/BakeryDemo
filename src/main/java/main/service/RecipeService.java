package main.service;

import main.domain.Ingredient;
import main.domain.Recipe;
import main.domain.RecipeLine;
import main.domain.RecipeType;
import main.exception.NotEnoughStockException;
import main.exception.NotFoundException;
import main.exception.UnexpectedValueException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeService {
	private List<Ingredient> allIngredients = new ArrayList<>();
	private List<Recipe> allRecipes = new ArrayList<>();

	public String createIngredient(String name, long stockLevel) {
		if(stockLevel < 0) {
			throw new UnexpectedValueException("Stock value needs to be positive");
		}
		if(name == null || name.isEmpty()) {
			throw new UnexpectedValueException("Name needs to be specified");
		}

		Ingredient newIngredient = new Ingredient(name, stockLevel);
		this.allIngredients.add(newIngredient);
		return newIngredient.getId();
	}

	public String createRecipe(String name, RecipeType type, List<RecipeLine> contents) {
		Recipe newRecipe = new Recipe(name, type, contents);
		this.allRecipes.add(newRecipe);
		return newRecipe.getId();
	}

	public Ingredient getIngredientById(String id) {
		return this.allIngredients.stream()
			.filter(i -> i.getId().equals(id))
			.findFirst()
			.orElseThrow(() -> new NotFoundException("Ingredient " + id));
	}

	public Recipe getRecipeById(String id) {
		return this.allRecipes.stream()
			.filter(r -> r.getId().equals(id))
			.findFirst()
			.orElseThrow(() -> new NotFoundException("Recipe " + id));
	}

	public void reduceStock(String id, long reduceBy) {
		Ingredient ingredient = this.getIngredientById(id);
		ingredient.reduceStockLevel(reduceBy);
	}

	public void updateStock(String id, long newStock) {
		Ingredient ingredient = this.getIngredientById(id);
		ingredient.setStockLevel(newStock);
	}

	public List<Recipe> getRecipesByType(RecipeType type) {
		return this.allRecipes.stream()
			.filter(r -> r.getRecipeType().equals(type))
			.sorted(Comparator.comparing(Recipe::getName))
			.toList();
	}

	public List<Recipe> getRecipesBetweenDates(LocalDate from, LocalDate to) {
		return this.allRecipes.stream()
			.filter(r -> r.getCreated().isAfter(from) && r.getCreated().isBefore(to))
			.toList();
	}

	public boolean hasEnoughStockForRecipe(String recipeId, long portions) throws NotEnoughStockException {
		Recipe recipe = this.getRecipeById(recipeId);

		Map<String, Long> usedIngredients = new HashMap<>();
		Map<Ingredient, Long> missingIngredients = new HashMap<>();

		for(RecipeLine line : recipe.getContents()) {
			String ingredientId = line.getIngredientId();
			Ingredient ingredient = getIngredientById(ingredientId);

			// Have we used this earlier in the recipe?
			Long alreadyUsed = usedIngredients.getOrDefault(line.getIngredientId(), 0L);

			// Make a not that we have not spent part of the stock
			long gramsNeeded = (line.getAmountGrams() * portions);
			usedIngredients.compute(ingredientId, (i, au) -> (au == null) ? gramsNeeded : gramsNeeded + au);

			// How much stock will there be left after this
			long gramsLeft = ingredient.getStockLevel() - alreadyUsed - gramsNeeded;

			// If we get below 0 we need to make a note for the exception later on
			if(gramsLeft < 0) {
				long missingGrams = -1 * gramsLeft;
				missingIngredients.compute(ingredient,
					(i, alreadyMissing) -> (alreadyMissing == null) ? missingGrams : missingGrams + alreadyMissing
				);
			}
		}

		if(!missingIngredients.isEmpty()) {
			throw new NotEnoughStockException(missingIngredients);
		}

		return true;
	}

	public void reduceStockForRecipe(String recipeId, long portions) throws NotEnoughStockException {
		Recipe recipe = getRecipeById(recipeId);

		hasEnoughStockForRecipe(recipeId, portions);

		for(RecipeLine line : recipe.getContents()) {
			Ingredient ingredient = this.getIngredientById(line.getIngredientId());
			ingredient.reduceStockLevel(line.getAmountGrams() * portions);
		}
	}

	public Map<Ingredient, Long> getStockLevels() {
		return this.allIngredients.stream()
			.collect(Collectors.toMap(i -> i, Ingredient::getStockLevel));
	}
}
