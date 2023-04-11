package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "description";
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID = new Long(3L);
    public static final Recipe RECIPE = new Recipe();

    IngredientToIngredientCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }
    @Test
    public void testNullConvert() throws Exception{
        assertNull(converter.convert(null));

    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(converter.convert(new Ingredient()));
    }

    @Test
    public void convertNullUom() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        //then
        assertNull(ingredientCommand.getUom());
        assertEquals(ID_VALUE,ingredientCommand.getId());
        assertEquals(DESCRIPTION,ingredientCommand.getDescription());
        assertEquals(AMOUNT,ingredientCommand.getAmount());
    }

    @Test
    public void convert() {
        //given
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        ingredient.setUom(unitOfMeasure);
        //when
        IngredientCommand ingredientCommand = converter.convert(ingredient);
        //then
        assertNotNull(ingredientCommand.getUom());
        assertEquals(ID_VALUE,ingredientCommand.getId());
        assertEquals(DESCRIPTION,ingredientCommand.getDescription());
        assertEquals(AMOUNT,ingredientCommand.getAmount());
        assertEquals(UOM_ID,ingredientCommand.getUom().getId());

    }
}