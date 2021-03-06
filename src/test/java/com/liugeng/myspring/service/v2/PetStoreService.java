package com.liugeng.myspring.service.v2;

import com.liugeng.myspring.dao.v2.AccountDao;
import com.liugeng.myspring.dao.v2.ItemDao;

public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private String env;
    private int version;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
