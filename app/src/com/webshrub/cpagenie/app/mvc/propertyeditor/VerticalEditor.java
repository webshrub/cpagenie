package com.webshrub.cpagenie.app.mvc.propertyeditor;

import com.webshrub.cpagenie.app.mvc.dto.Vertical;
import com.webshrub.cpagenie.app.mvc.service.VerticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class VerticalEditor extends PropertyEditorSupport {

    @Autowired
    private VerticalService verticalService;

    @Override
    public String getAsText() {
        Vertical vertical = (Vertical) getValue();
        if (vertical == null) {
            return "No vertical set";
        }
        return vertical.getId().toString();
    }

    @Override
    public void setAsText(String verticalIdStr) throws IllegalArgumentException {
        setValue(verticalService.getVertical(Integer.parseInt(verticalIdStr)));
    }
}