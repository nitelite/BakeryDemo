package main.exception;

import main.domain.Ingredient;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NotEnoughStockException extends Exception {
	private final HashMap<Ingredient, Long> missing = new HashMap<>();

	public NotEnoughStockException(Map<Ingredient, Long> missing) {
		this.missing.putAll(missing);
	}

	@Override
	public String getMessage() {
		return this.missing.entrySet().stream()
			.map(e -> "Missing " + e.getValue() + " grams of " + e.getKey().getName() + " (" + e.getKey().getId() + ")")
			.collect(Collectors.joining("\n"));
	}
}
