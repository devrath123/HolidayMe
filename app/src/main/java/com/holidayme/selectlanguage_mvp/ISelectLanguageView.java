package com.holidayme.selectlanguage_mvp;

import com.holidayme.data.Item;

import java.util.List;

/**
 * Created by devrath.rathee on 02-11-2016.
 */

public interface ISelectLanguageView {

    void setAdapterData(List<Item> itemList);
    void traverseToLogin();
}
