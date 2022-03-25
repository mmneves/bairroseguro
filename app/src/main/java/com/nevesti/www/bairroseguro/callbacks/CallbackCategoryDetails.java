package com.nevesti.www.bairroseguro.callbacks;

import com.nevesti.www.bairroseguro.model.Category;
import com.nevesti.www.bairroseguro.model.News;

import java.util.ArrayList;
import java.util.List;

public class CallbackCategoryDetails {

    public String status = "";
    public int count = -1;
    public int count_total = -1;
    public int pages = -1;
    public Category category = null;
    public List<News> posts = new ArrayList<>();
}
