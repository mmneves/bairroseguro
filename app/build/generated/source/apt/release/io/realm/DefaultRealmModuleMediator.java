package io.realm;


import android.util.JsonReader;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>(2);
        modelClasses.add(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        modelClasses.add(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        Map<Class<? extends RealmModel>, OsObjectSchemaInfo> infoMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>(2);
        infoMap.put(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class, io.realm.NewsRealmRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class, io.realm.CategoryRealmRealmProxy.getExpectedObjectSchemaInfo());
        return infoMap;
    }

    @Override
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> clazz, OsSchemaInfo schemaInfo) {
        checkClass(clazz);

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return io.realm.NewsRealmRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return io.realm.CategoryRealmRealmProxy.createColumnInfo(schemaInfo);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public List<String> getFieldNames(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return io.realm.NewsRealmRealmProxy.getFieldNames();
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return io.realm.CategoryRealmRealmProxy.getFieldNames();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getTableName(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return io.realm.NewsRealmRealmProxy.getTableName();
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return io.realm.CategoryRealmRealmProxy.getTableName();
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
                return clazz.cast(new io.realm.NewsRealmRealmProxy());
            }
            if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
                return clazz.cast(new io.realm.CategoryRealmRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return clazz.cast(io.realm.NewsRealmRealmProxy.copyOrUpdate(realm, (com.nevesti.www.bairroseguro.realm.table.NewsRealm) obj, update, cache));
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return clazz.cast(io.realm.CategoryRealmRealmProxy.copyOrUpdate(realm, (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) obj, update, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            io.realm.NewsRealmRealmProxy.insert(realm, (com.nevesti.www.bairroseguro.realm.table.NewsRealm) object, cache);
        } else if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            io.realm.CategoryRealmRealmProxy.insert(realm, (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
                io.realm.NewsRealmRealmProxy.insert(realm, (com.nevesti.www.bairroseguro.realm.table.NewsRealm) object, cache);
            } else if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
                io.realm.CategoryRealmRealmProxy.insert(realm, (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
                    io.realm.NewsRealmRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
                    io.realm.CategoryRealmRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            io.realm.NewsRealmRealmProxy.insertOrUpdate(realm, (com.nevesti.www.bairroseguro.realm.table.NewsRealm) obj, cache);
        } else if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            io.realm.CategoryRealmRealmProxy.insertOrUpdate(realm, (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
                io.realm.NewsRealmRealmProxy.insertOrUpdate(realm, (com.nevesti.www.bairroseguro.realm.table.NewsRealm) object, cache);
            } else if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
                io.realm.CategoryRealmRealmProxy.insertOrUpdate(realm, (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
                    io.realm.NewsRealmRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
                    io.realm.CategoryRealmRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return clazz.cast(io.realm.NewsRealmRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return clazz.cast(io.realm.CategoryRealmRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return clazz.cast(io.realm.NewsRealmRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return clazz.cast(io.realm.CategoryRealmRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class)) {
            return clazz.cast(io.realm.NewsRealmRealmProxy.createDetachedCopy((com.nevesti.www.bairroseguro.realm.table.NewsRealm) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class)) {
            return clazz.cast(io.realm.CategoryRealmRealmProxy.createDetachedCopy((com.nevesti.www.bairroseguro.realm.table.CategoryRealm) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
