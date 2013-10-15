package com.webshrub.cpagenie.app.mvc.view;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class FlexJSONView extends AbstractView {
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    private JSONSerializer serializer = new JSONSerializer();
    private boolean prettyPrint = true;
    private DateTransformer dateTransformer = new DateTransformer("yyyy-MM-dd");
    private boolean deepSerialize = false;
    private List<String> includeProperties = new ArrayList<String>();
    private List<String> excludeProperties = new ArrayList<String>();

    public JSONSerializer getSerializer() {
        return serializer;
    }

    public void setSerializer(JSONSerializer serializer) {
        this.serializer = serializer;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
    }

    public DateTransformer getDateTransformer() {
        return dateTransformer;
    }

    public void setDateTransformer(DateTransformer dateTransformer) {
        this.dateTransformer = dateTransformer;
    }

    public boolean isDeepSerialize() {
        return deepSerialize;
    }

    public void setDeepSerialize(boolean deepSerialize) {
        this.deepSerialize = deepSerialize;
    }

    public List<String> getIncludeProperties() {
        return includeProperties;
    }

    public void setIncludeProperties(List<String> includeProperties) {
        this.includeProperties = includeProperties;
    }

    public List<String> getExcludeProperties() {
        return excludeProperties;
    }

    public void setExcludeProperties(List<String> excludeProperties) {
        this.excludeProperties = excludeProperties;
    }

    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        model = filterModel(model);
        configureSerializer(model);
        String json;
        if (deepSerialize) {
            json = serializer.deepSerialize(model);
        } else {
            json = serializer.serialize(model);
        }
        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.getOutputStream().write(json.getBytes());
    }

    protected Map<String, Object> filterModel(Map<String, Object> model) {
        Map<String, Object> resultMap = new HashMap<String, Object>(model.size());
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            if (!(entry.getValue() instanceof BindingResult)) {
                resultMap.put(entry.getKey(), entry.getValue());
            }
        }
        return resultMap;
    }

    protected void configureSerializer(Map<String, Object> model) {
        if (includeProperties.size() == 0) {
            for (String includeProperty : model.keySet()) {
                serializer.include(includeProperty);
            }
        } else {
            for (String includeProperty : includeProperties) {
                serializer.include(includeProperty);
            }
        }
        for (String excludeProperty : excludeProperties) {
            serializer.exclude(excludeProperty);
        }
        serializer.exclude("*.class");
        serializer.prettyPrint(prettyPrint);
        serializer.transform(dateTransformer, Date.class);
    }
}
