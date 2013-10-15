package com.webshrub.cpagenie.app.mvc.service;

import com.webshrub.cpagenie.app.mvc.dto.Vertical;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ahsan.Javed
 * Date: Aug 7, 2010
 * Time: 1:38:33 PM
 */
public interface VerticalService {

    public List<Vertical> getVerticalList();

    public Vertical getVertical(Integer id);

    public void saveVertical(Vertical vertical);

    public void updateVertical(Vertical vertical);

    public void deleteVertical(Vertical vertical);
}
