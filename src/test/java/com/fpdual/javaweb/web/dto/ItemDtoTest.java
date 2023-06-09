package com.fpdual.javaweb.web.dto;

import com.fpdual.javaweb.web.servlet.dto.ItemDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ItemDtoTest {


    @Test
    public void GettersAndSetters(){
        ItemDto dto = new  ItemDto();
        dto.setId("test");
        dto.setName("");


        assertTrue(dto.getId().equals("test"));
        assertTrue(dto.getName().equals(""));

    }

}
