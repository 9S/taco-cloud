package com.example.tacocloud;

import com.example.tacocloud.Ingredient.Type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:test_db", "spring.jpa.hibernate.ddl-auto=create-drop"})
class JdbcIngredientRepositoryTest {
    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    void findAll() {
        var ingredients = Arrays.asList(
                new Ingredient("AAAA", "Flour Tortilla", Type.WRAP),
                new Ingredient("BBBB", "Corn Tortilla", Type.WRAP),
                new Ingredient("CCCC", "Ground Beef", Type.PROTEIN),
                new Ingredient("DDDD", "Carnitas", Type.PROTEIN),
                new Ingredient("EEEE", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("FFFF", "Lettuce", Type.VEGGIES),
                new Ingredient("GGGG", "Cheddar", Type.CHEESE),
                new Ingredient("HHHH", "Monterrey Jack", Type.CHEESE),
                new Ingredient("IIII", "Salsa", Type.SAUCE),
                new Ingredient("JJJJ", "Sour Cream", Type.SAUCE)
        );
        ingredients.forEach(ingredient -> ingredientRepository.save(ingredient));

        var loaded = ingredientRepository.findAll();
        for (var ingredient : ingredients) {
            assertThat(loaded).contains(ingredient);
        }
    }

    @Test
    void save_and_findByID() {
        var flourTortilla = new Ingredient("KKKK", "Flour Tortilla", Type.WRAP);
        var afterSave = ingredientRepository.save(flourTortilla);
        assertThat(flourTortilla).isEqualTo(afterSave);

        var loaded = ingredientRepository.findById(flourTortilla.getId());
        assertThat(loaded).isPresent();
        assertThat(loaded.get()).isEqualTo(flourTortilla);
    }
}