package com.thegongoliers.utils;

import java.util.List;

public interface IModular<T> {
    void setModules(IModule<T>... modules);

    void addModule(IModule<T> module);

    void addModule(int index, IModule<T> module);

    void removeModule(IModule<T> module);

    List<IModule<T>> getInstalledModules();

    <K> K getInstalledModule(Class<K> cls);
}
