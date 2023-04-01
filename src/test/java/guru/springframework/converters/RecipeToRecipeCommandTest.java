package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),new IngredientToIngredientCommand(), new NotesToNotesCommand());
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject()throws Exception{
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        Category category = new Category();
        category.setId(CAT_ID_1);
        recipe.getCategories().add(category);
        Category category1 = new Category();
        category1.setId(CAT_ID2);
        recipe.getCategories().add(category1);
        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);
        recipe.getIngredients().add(ingredient);
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGRED_ID_2);
        recipe.getIngredients().add(ingredient1);

        //then
        RecipeCommand recipeCommand = converter.convert(recipe);
        assertNotNull(recipeCommand);
        assertEquals(RECIPE_ID,recipeCommand.getId());
        assertEquals(COOK_TIME,recipeCommand.getCookTime());
        assertEquals(PREP_TIME,recipeCommand.getPrepTime());
        assertEquals(DESCRIPTION,recipeCommand.getDescription());
        assertEquals(DIRECTIONS,recipeCommand.getDirections());
        assertEquals(DIFFICULTY,recipeCommand.getDifficulty());
        assertEquals(SERVINGS,recipeCommand.getServings());
        assertEquals(SOURCE,recipeCommand.getSource());
        assertEquals(URL,recipeCommand.getUrl());
        assertEquals(NOTES_ID,recipeCommand.getNotes().getId());
        assertEquals(2,recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());
    }
}