package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeCommandToRecipeTest {
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

    RecipeCommandToRecipe converter;
    private NotesCommandToNotes notesConverter;
    private CategoryCommandToCategory categoryConverter;
    private IngredientCommandToIngredient ingredientConverter;

    @Before
    public void setUp() throws Exception {
        converter = new RecipeCommandToRecipe(new NotesCommandToNotes(), new CategoryCommandToCategory(), new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    public void convertNullObject() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void convertEmptyObject()throws Exception{
        assertNotNull(converter.convert(new RecipeCommand()));
    }

    @Test
    public void convert() {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);

        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(CAT_ID_1);
        recipeCommand.getCategories().add(categoryCommand);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID2);
        recipeCommand.getCategories().add(categoryCommand1);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGRED_ID_1);
        recipeCommand.getIngredients().add(ingredientCommand);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGRED_ID_2);
        recipeCommand.getIngredients().add(ingredientCommand1);

        //when
        Recipe recipe= converter.convert(recipeCommand);
        //then
        assertNotNull(recipeCommand);
        assertEquals(RECIPE_ID,recipe.getId());
        assertEquals(PREP_TIME,recipe.getPrepTime());
        assertEquals(COOK_TIME,recipe.getCookTime());
        assertEquals(DESCRIPTION,recipe.getDescription());
        assertEquals(DIFFICULTY,recipe.getDifficulty());
        assertEquals(DIRECTIONS,recipe.getDirections());
        assertEquals(SERVINGS,recipe.getServings());
        assertEquals(SOURCE,recipe.getSource());
        assertEquals(URL,recipe.getUrl());
        assertEquals(NOTES_ID,recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2,recipe.getIngredients().size());
    }
}