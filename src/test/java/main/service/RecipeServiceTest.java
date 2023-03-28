package main.service;

import main.domain.RecipeLine;
import main.domain.RecipeType;
import main.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecipeServiceTest {


	@Test void testHasEnoughIngredientsOk() {
		RecipeService recipeService = new RecipeService();

		String sugarId = recipeService.createIngredient("Sugar", 100);
		String eggId = recipeService.createIngredient("Egg", 50);

		String pancakesId = recipeService.createRecipe("Pancakes", RecipeType.OTHER,List.of(
			new RecipeLine(sugarId, 50),
			new RecipeLine(eggId, 20)
		));

		assertDoesNotThrow(() -> {
			recipeService.hasEnoughStockForRecipe(pancakesId, 2);
		});
	}

	@Test void testHasEnoughIngredientsNotOK() {
		RecipeService recipeService = new RecipeService();

		String sugarId = recipeService.createIngredient("Sugar", 40);
		String eggId = recipeService.createIngredient("Egg", 50);

		String pancakesId = recipeService.createRecipe("Pancakes", RecipeType.OTHER,List.of(
			new RecipeLine(sugarId, 50),
			new RecipeLine(eggId, 20)
		));

		NotEnoughStockException ex = assertThrows(NotEnoughStockException.class, () -> {
			recipeService.hasEnoughStockForRecipe(pancakesId, 2);
		});

		System.out.println("Recipe cannot be made because: ");
		System.out.println(ex.getMessage());
	}

	@Test void testHasEnoughIngredientsNotOKRepeatedIngredients() {
		RecipeService recipeService = new RecipeService();

		String sugarId = recipeService.createIngredient("Sugar", 100);
		String eggId = recipeService.createIngredient("Egg", 50);

		String pancakesId = recipeService.createRecipe("Pancakes", RecipeType.OTHER,List.of(
			new RecipeLine(sugarId, 40),
			new RecipeLine(eggId, 20),
			new RecipeLine(sugarId, 15)
		));

		NotEnoughStockException ex = assertThrows(NotEnoughStockException.class, () -> {
			recipeService.hasEnoughStockForRecipe(pancakesId, 2);
		});

		System.out.println("Recipe cannot be made because: ");
		System.out.println(ex.getMessage());
	}


	@Test void testReduceStocksOk() {
		RecipeService recipeService = new RecipeService();

		String sugarId = recipeService.createIngredient("Sugar", 100);
		String eggId = recipeService.createIngredient("Egg", 100);

		String pancakesId = recipeService.createRecipe("Pancakes", RecipeType.OTHER,List.of(
			new RecipeLine(sugarId, 10),
			new RecipeLine(eggId, 5)
		));

		assertDoesNotThrow(() -> {
			recipeService.reduceStockForRecipe(pancakesId, 10);
		});

		recipeService.getStockLevels()
			.forEach((ing, stock) -> System.out.println("Ingredient " + ing.getName() + " has stock level: " + stock + "g"));
	}
}
