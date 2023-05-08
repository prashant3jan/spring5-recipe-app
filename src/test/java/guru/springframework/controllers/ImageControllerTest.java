package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;
    ImageController controller;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        controller = new ImageController(imageService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testShowUploadForm() throws Exception{
        RecipeCommand recipeCommand= new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/imageuploadform"))
                        .andExpect(model().attributeExists("recipe"));

        verify(recipeService,times(1)).findCommandById(anyLong());
    }

    @Test
    public void testHandleImagePost() throws Exception{
        MockMultipartFile multipartFile = new MockMultipartFile("imagefile", "test.jpg", "image/jpeg", "test image content".getBytes());
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image")
                        .file(multipartFile)
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));
        verify(imageService,times(1)).saveImageFile(anyLong(),any());
    }
    @Test
    public void testRenderImageFromDB() throws Exception{
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String s = "fake image test";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i=0;
        for (byte primByte : s.getBytes()){
            bytesBoxed[i++]=primByte;
        }
        command.setImage(bytesBoxed);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length,responseBytes.length);

    }
    @Test
    public void testGetImageNumberFormatException() throws Exception{
        mockMvc.perform(get("/recipe/asdf/image"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}