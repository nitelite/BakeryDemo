package main.exception;

import main.domain.Ingredient;

import java.util.Map;
import java.util.stream.Collectors;

public class NotEnoughStockException extends Exception {
	private Map<Ingredient, Long> missing;

	public NotEnoughStockException(Map<Ingredient, Long> missing) {
		this.missing = missing;
	}

	public String getMessage() {
		return this.missing.entrySet().stream()
			.map(e -> "Missing " + e.getValue() + " grams of " + e.getKey().getName() + " (" + e.getKey().getId() + ")")
			.collect(Collectors.joining("\n"));
	}
}
