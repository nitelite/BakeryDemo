package main.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipeLine {
	String ingredientId;
	long amountGrams;
}
