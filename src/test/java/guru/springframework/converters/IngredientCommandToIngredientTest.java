package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    public static final String DESCRIPTION = "description";
    public static final Long ID_VALUE= new Long(1L);
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final Long UOM_ID= new Long(2L);

    IngredientCommandToIngredient converter;

    @Before
    public void setUp() throws Exception {
        converter= new IngredientCommandToIngredient();
    }

    @Test
    public void testNullObject() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(converter.convert(new IngredientCommand()));
    }

    @Test
    public void convert() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        ingredientCommand.setUom(unitOfMeasure);
        //when
        Ingredient ingredient = converter.convert(ingredientCommand);
        //then
        assertNotNull(ingredient);
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(ID_VALUE,ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    public void convertWithNullUOM() throws Exception{
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setAmount(AMOUNT);
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        //when
        Ingredient ingredient = converter.convert(ingredientCommand);
        //then
        assertNotNull(ingredient);
        assertEquals(DESCRIPTION,ingredient.getDescription());
        assertEquals(ID_VALUE,ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertNull(ingredient.getUom());
    }
}