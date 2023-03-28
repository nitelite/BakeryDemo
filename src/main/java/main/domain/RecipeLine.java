package main.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RecipeLine {
	String ingredientId;
	long amountGrams;
}
