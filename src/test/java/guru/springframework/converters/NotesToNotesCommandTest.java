package guru.springframework.converters;

import guru.springframework.commands.NotesCommand;
import guru.springframework.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {
    public static final Long ID_VALUE = new Long(1L);
    public static final String RECIPE_NOTES = "Notes";
    NotesToNotesCommand converter;

    @Before
    public void setUp() throws Exception {
        converter = new NotesToNotesCommand();
    }

    public void testNullObject()throws Exception{
        assertNull(converter.convert(null));
    }

    public void testEmptyObject()throws Exception{
        assertNotNull(converter.convert(new Notes()));
    }

    @Test
    public void convert() {
        //given
        Notes notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);
        //when
        NotesCommand notesCommand = converter.convert(notes);
        //then
        assertNotNull(notesCommand);
        assertEquals(ID_VALUE,notesCommand.getId());
        assertEquals(RECIPE_NOTES,notesCommand.getRecipeNotes());
    }
}