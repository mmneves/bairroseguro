package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.ProxyUtils;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class CategoryRealmRealmProxy extends com.nevesti.www.bairroseguro.realm.table.CategoryRealm
    implements RealmObjectProxy, CategoryRealmRealmProxyInterface {

    static final class CategoryRealmColumnInfo extends ColumnInfo {
        long cidIndex;
        long category_nameIndex;
        long category_imageIndex;
        long post_countIndex;

        CategoryRealmColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("CategoryRealm");
            this.cidIndex = addColumnDetails("cid", objectSchemaInfo);
            this.category_nameIndex = addColumnDetails("category_name", objectSchemaInfo);
            this.category_imageIndex = addColumnDetails("category_image", objectSchemaInfo);
            this.post_countIndex = addColumnDetails("post_count", objectSchemaInfo);
        }

        CategoryRealmColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new CategoryRealmColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final CategoryRealmColumnInfo src = (CategoryRealmColumnInfo) rawSrc;
            final CategoryRealmColumnInfo dst = (CategoryRealmColumnInfo) rawDst;
            dst.cidIndex = src.cidIndex;
            dst.category_nameIndex = src.category_nameIndex;
            dst.category_imageIndex = src.category_imageIndex;
            dst.post_countIndex = src.post_countIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(4);
        fieldNames.add("cid");
        fieldNames.add("category_name");
        fieldNames.add("category_image");
        fieldNames.add("post_count");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private CategoryRealmColumnInfo columnInfo;
    private ProxyState<com.nevesti.www.bairroseguro.realm.table.CategoryRealm> proxyState;

    CategoryRealmRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (CategoryRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.nevesti.www.bairroseguro.realm.table.CategoryRealm>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$cid() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.cidIndex);
    }

    @Override
    public void realmSet$cid(long value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'cid' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$category_name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.category_nameIndex);
    }

    @Override
    public void realmSet$category_name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.category_nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.category_nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.category_nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.category_nameIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$category_image() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.category_imageIndex);
    }

    @Override
    public void realmSet$category_image(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.category_imageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.category_imageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.category_imageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.category_imageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$post_count() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.post_countIndex);
    }

    @Override
    public void realmSet$post_count(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.post_countIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.post_countIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("CategoryRealm", 4, 0);
        builder.addPersistedProperty("cid", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("category_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("category_image", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("post_count", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static CategoryRealmColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new CategoryRealmColumnInfo(schemaInfo);
    }

    public static String getTableName() {
        return "class_CategoryRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.nevesti.www.bairroseguro.realm.table.CategoryRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.nevesti.www.bairroseguro.realm.table.CategoryRealm obj = null;
        if (update) {
            Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
            CategoryRealmColumnInfo columnInfo = (CategoryRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
            long pkColumnIndex = columnInfo.cidIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("cid")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("cid"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.CategoryRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("cid")) {
                if (json.isNull("cid")) {
                    obj = (io.realm.CategoryRealmRealmProxy) realm.createObjectInternal(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.CategoryRealmRealmProxy) realm.createObjectInternal(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class, json.getLong("cid"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'cid'.");
            }
        }

        final CategoryRealmRealmProxyInterface objProxy = (CategoryRealmRealmProxyInterface) obj;
        if (json.has("category_name")) {
            if (json.isNull("category_name")) {
                objProxy.realmSet$category_name(null);
            } else {
                objProxy.realmSet$category_name((String) json.getString("category_name"));
            }
        }
        if (json.has("category_image")) {
            if (json.isNull("category_image")) {
                objProxy.realmSet$category_image(null);
            } else {
                objProxy.realmSet$category_image((String) json.getString("category_image"));
            }
        }
        if (json.has("post_count")) {
            if (json.isNull("post_count")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'post_count' to null.");
            } else {
                objProxy.realmSet$post_count((long) json.getLong("post_count"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.nevesti.www.bairroseguro.realm.table.CategoryRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.nevesti.www.bairroseguro.realm.table.CategoryRealm obj = new com.nevesti.www.bairroseguro.realm.table.CategoryRealm();
        final CategoryRealmRealmProxyInterface objProxy = (CategoryRealmRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("cid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$cid((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'cid' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("category_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$category_name(null);
                }
            } else if (name.equals("category_image")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category_image((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$category_image(null);
                }
            } else if (name.equals("post_count")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$post_count((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'post_count' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'cid'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.nevesti.www.bairroseguro.realm.table.CategoryRealm copyOrUpdate(Realm realm, com.nevesti.www.bairroseguro.realm.table.CategoryRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) cachedRealmObject;
        }

        com.nevesti.www.bairroseguro.realm.table.CategoryRealm realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
            CategoryRealmColumnInfo columnInfo = (CategoryRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
            long pkColumnIndex = columnInfo.cidIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.CategoryRealmRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.nevesti.www.bairroseguro.realm.table.CategoryRealm copy(Realm realm, com.nevesti.www.bairroseguro.realm.table.CategoryRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.nevesti.www.bairroseguro.realm.table.CategoryRealm realmObject = realm.createObjectInternal(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class, ((CategoryRealmRealmProxyInterface) newObject).realmGet$cid(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        CategoryRealmRealmProxyInterface realmObjectSource = (CategoryRealmRealmProxyInterface) newObject;
        CategoryRealmRealmProxyInterface realmObjectCopy = (CategoryRealmRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$category_name(realmObjectSource.realmGet$category_name());
        realmObjectCopy.realmSet$category_image(realmObjectSource.realmGet$category_image());
        realmObjectCopy.realmSet$post_count(realmObjectSource.realmGet$post_count());
        return realmObject;
    }

    public static long insert(Realm realm, com.nevesti.www.bairroseguro.realm.table.CategoryRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long tableNativePtr = table.getNativePtr();
        CategoryRealmColumnInfo columnInfo = (CategoryRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long pkColumnIndex = columnInfo.cidIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((CategoryRealmRealmProxyInterface) object).realmGet$cid();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$category_name = ((CategoryRealmRealmProxyInterface) object).realmGet$category_name();
        if (realmGet$category_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
        }
        String realmGet$category_image = ((CategoryRealmRealmProxyInterface) object).realmGet$category_image();
        if (realmGet$category_image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_imageIndex, rowIndex, realmGet$category_image, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.post_countIndex, rowIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$post_count(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long tableNativePtr = table.getNativePtr();
        CategoryRealmColumnInfo columnInfo = (CategoryRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long pkColumnIndex = columnInfo.cidIndex;
        com.nevesti.www.bairroseguro.realm.table.CategoryRealm object = null;
        while (objects.hasNext()) {
            object = (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((CategoryRealmRealmProxyInterface) object).realmGet$cid();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$category_name = ((CategoryRealmRealmProxyInterface) object).realmGet$category_name();
            if (realmGet$category_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
            }
            String realmGet$category_image = ((CategoryRealmRealmProxyInterface) object).realmGet$category_image();
            if (realmGet$category_image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_imageIndex, rowIndex, realmGet$category_image, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.post_countIndex, rowIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$post_count(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.nevesti.www.bairroseguro.realm.table.CategoryRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long tableNativePtr = table.getNativePtr();
        CategoryRealmColumnInfo columnInfo = (CategoryRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long pkColumnIndex = columnInfo.cidIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((CategoryRealmRealmProxyInterface) object).realmGet$cid();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
        }
        cache.put(object, rowIndex);
        String realmGet$category_name = ((CategoryRealmRealmProxyInterface) object).realmGet$category_name();
        if (realmGet$category_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.category_nameIndex, rowIndex, false);
        }
        String realmGet$category_image = ((CategoryRealmRealmProxyInterface) object).realmGet$category_image();
        if (realmGet$category_image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_imageIndex, rowIndex, realmGet$category_image, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.category_imageIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.post_countIndex, rowIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$post_count(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long tableNativePtr = table.getNativePtr();
        CategoryRealmColumnInfo columnInfo = (CategoryRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.CategoryRealm.class);
        long pkColumnIndex = columnInfo.cidIndex;
        com.nevesti.www.bairroseguro.realm.table.CategoryRealm object = null;
        while (objects.hasNext()) {
            object = (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((CategoryRealmRealmProxyInterface) object).realmGet$cid();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$cid());
            }
            cache.put(object, rowIndex);
            String realmGet$category_name = ((CategoryRealmRealmProxyInterface) object).realmGet$category_name();
            if (realmGet$category_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.category_nameIndex, rowIndex, false);
            }
            String realmGet$category_image = ((CategoryRealmRealmProxyInterface) object).realmGet$category_image();
            if (realmGet$category_image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_imageIndex, rowIndex, realmGet$category_image, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.category_imageIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.post_countIndex, rowIndex, ((CategoryRealmRealmProxyInterface) object).realmGet$post_count(), false);
        }
    }

    public static com.nevesti.www.bairroseguro.realm.table.CategoryRealm createDetachedCopy(com.nevesti.www.bairroseguro.realm.table.CategoryRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.nevesti.www.bairroseguro.realm.table.CategoryRealm unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.nevesti.www.bairroseguro.realm.table.CategoryRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) cachedObject.object;
            }
            unmanagedObject = (com.nevesti.www.bairroseguro.realm.table.CategoryRealm) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        CategoryRealmRealmProxyInterface unmanagedCopy = (CategoryRealmRealmProxyInterface) unmanagedObject;
        CategoryRealmRealmProxyInterface realmSource = (CategoryRealmRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$cid(realmSource.realmGet$cid());
        unmanagedCopy.realmSet$category_name(realmSource.realmGet$category_name());
        unmanagedCopy.realmSet$category_image(realmSource.realmGet$category_image());
        unmanagedCopy.realmSet$post_count(realmSource.realmGet$post_count());

        return unmanagedObject;
    }

    static com.nevesti.www.bairroseguro.realm.table.CategoryRealm update(Realm realm, com.nevesti.www.bairroseguro.realm.table.CategoryRealm realmObject, com.nevesti.www.bairroseguro.realm.table.CategoryRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        CategoryRealmRealmProxyInterface realmObjectTarget = (CategoryRealmRealmProxyInterface) realmObject;
        CategoryRealmRealmProxyInterface realmObjectSource = (CategoryRealmRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$category_name(realmObjectSource.realmGet$category_name());
        realmObjectTarget.realmSet$category_image(realmObjectSource.realmGet$category_image());
        realmObjectTarget.realmSet$post_count(realmObjectSource.realmGet$post_count());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("CategoryRealm = proxy[");
        stringBuilder.append("{cid:");
        stringBuilder.append(realmGet$cid());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category_name:");
        stringBuilder.append(realmGet$category_name() != null ? realmGet$category_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category_image:");
        stringBuilder.append(realmGet$category_image() != null ? realmGet$category_image() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{post_count:");
        stringBuilder.append(realmGet$post_count());
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryRealmRealmProxy aCategoryRealm = (CategoryRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCategoryRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCategoryRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCategoryRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
