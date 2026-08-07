package com.SeeTohJJ.Backend.garden.dao;

import com.SeeTohJJ.Backend.garden.model.UserCurrency;

public interface UserCurrencyDao {
    UserCurrency getCurrency(Long userId);
    void update(UserCurrency currency);
}
