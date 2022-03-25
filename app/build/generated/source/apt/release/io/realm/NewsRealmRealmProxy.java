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
public class NewsRealmRealmProxy extends com.nevesti.www.bairroseguro.realm.table.NewsRealm
    implements RealmObjectProxy, NewsRealmRealmProxyInterface {

    static final class NewsRealmColumnInfo extends ColumnInfo {
        long nidIndex;
        long news_titleIndex;
        long category_nameIndex;
        long news_dateIndex;
        long news_imageIndex;
        long news_descriptionIndex;
        long video_urlIndex;
        long video_idIndex;
        long content_typeIndex;
        long comments_countIndex;
        long added_dateIndex;

        NewsRealmColumnInfo(OsSchemaInfo schemaInfo) {
            super(11);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("NewsRealm");
            this.nidIndex = addColumnDetails("nid", objectSchemaInfo);
            this.news_titleIndex = addColumnDetails("news_title", objectSchemaInfo);
            this.category_nameIndex = addColumnDetails("category_name", objectSchemaInfo);
            this.news_dateIndex = addColumnDetails("news_date", objectSchemaInfo);
            this.news_imageIndex = addColumnDetails("news_image", objectSchemaInfo);
            this.news_descriptionIndex = addColumnDetails("news_description", objectSchemaInfo);
            this.video_urlIndex = addColumnDetails("video_url", objectSchemaInfo);
            this.video_idIndex = addColumnDetails("video_id", objectSchemaInfo);
            this.content_typeIndex = addColumnDetails("content_type", objectSchemaInfo);
            this.comments_countIndex = addColumnDetails("comments_count", objectSchemaInfo);
            this.added_dateIndex = addColumnDetails("added_date", objectSchemaInfo);
        }

        NewsRealmColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new NewsRealmColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final NewsRealmColumnInfo src = (NewsRealmColumnInfo) rawSrc;
            final NewsRealmColumnInfo dst = (NewsRealmColumnInfo) rawDst;
            dst.nidIndex = src.nidIndex;
            dst.news_titleIndex = src.news_titleIndex;
            dst.category_nameIndex = src.category_nameIndex;
            dst.news_dateIndex = src.news_dateIndex;
            dst.news_imageIndex = src.news_imageIndex;
            dst.news_descriptionIndex = src.news_descriptionIndex;
            dst.video_urlIndex = src.video_urlIndex;
            dst.video_idIndex = src.video_idIndex;
            dst.content_typeIndex = src.content_typeIndex;
            dst.comments_countIndex = src.comments_countIndex;
            dst.added_dateIndex = src.added_dateIndex;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>(11);
        fieldNames.add("nid");
        fieldNames.add("news_title");
        fieldNames.add("category_name");
        fieldNames.add("news_date");
        fieldNames.add("news_image");
        fieldNames.add("news_description");
        fieldNames.add("video_url");
        fieldNames.add("video_id");
        fieldNames.add("content_type");
        fieldNames.add("comments_count");
        fieldNames.add("added_date");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    private NewsRealmColumnInfo columnInfo;
    private ProxyState<com.nevesti.www.bairroseguro.realm.table.NewsRealm> proxyState;

    NewsRealmRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (NewsRealmColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.nevesti.www.bairroseguro.realm.table.NewsRealm>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$nid() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.nidIndex);
    }

    @Override
    public void realmSet$nid(long value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'nid' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$news_title() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.news_titleIndex);
    }

    @Override
    public void realmSet$news_title(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.news_titleIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.news_titleIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.news_titleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.news_titleIndex, value);
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
    public String realmGet$news_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.news_dateIndex);
    }

    @Override
    public void realmSet$news_date(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.news_dateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.news_dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.news_dateIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.news_dateIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$news_image() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.news_imageIndex);
    }

    @Override
    public void realmSet$news_image(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.news_imageIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.news_imageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.news_imageIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.news_imageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$news_description() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.news_descriptionIndex);
    }

    @Override
    public void realmSet$news_description(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.news_descriptionIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.news_descriptionIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.news_descriptionIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.news_descriptionIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$video_url() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.video_urlIndex);
    }

    @Override
    public void realmSet$video_url(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.video_urlIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.video_urlIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.video_urlIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.video_urlIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$video_id() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.video_idIndex);
    }

    @Override
    public void realmSet$video_id(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.video_idIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.video_idIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.video_idIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.video_idIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$content_type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.content_typeIndex);
    }

    @Override
    public void realmSet$content_type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.content_typeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.content_typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.content_typeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.content_typeIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$comments_count() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.comments_countIndex);
    }

    @Override
    public void realmSet$comments_count(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.comments_countIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.comments_countIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public long realmGet$added_date() {
        proxyState.getRealm$realm().checkIfValid();
        return (long) proxyState.getRow$realm().getLong(columnInfo.added_dateIndex);
    }

    @Override
    public void realmSet$added_date(long value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.added_dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.added_dateIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("NewsRealm", 11, 0);
        builder.addPersistedProperty("nid", RealmFieldType.INTEGER, Property.PRIMARY_KEY, Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("news_title", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("category_name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("news_date", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("news_image", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("news_description", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("video_url", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("video_id", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("content_type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("comments_count", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("added_date", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static NewsRealmColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new NewsRealmColumnInfo(schemaInfo);
    }

    public static String getTableName() {
        return "class_NewsRealm";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static com.nevesti.www.bairroseguro.realm.table.NewsRealm createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = Collections.<String> emptyList();
        com.nevesti.www.bairroseguro.realm.table.NewsRealm obj = null;
        if (update) {
            Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
            NewsRealmColumnInfo columnInfo = (NewsRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
            long pkColumnIndex = columnInfo.nidIndex;
            long rowIndex = Table.NO_MATCH;
            if (!json.isNull("nid")) {
                rowIndex = table.findFirstLong(pkColumnIndex, json.getLong("nid"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class), false, Collections.<String> emptyList());
                    obj = new io.realm.NewsRealmRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("nid")) {
                if (json.isNull("nid")) {
                    obj = (io.realm.NewsRealmRealmProxy) realm.createObjectInternal(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.NewsRealmRealmProxy) realm.createObjectInternal(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class, json.getLong("nid"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'nid'.");
            }
        }

        final NewsRealmRealmProxyInterface objProxy = (NewsRealmRealmProxyInterface) obj;
        if (json.has("news_title")) {
            if (json.isNull("news_title")) {
                objProxy.realmSet$news_title(null);
            } else {
                objProxy.realmSet$news_title((String) json.getString("news_title"));
            }
        }
        if (json.has("category_name")) {
            if (json.isNull("category_name")) {
                objProxy.realmSet$category_name(null);
            } else {
                objProxy.realmSet$category_name((String) json.getString("category_name"));
            }
        }
        if (json.has("news_date")) {
            if (json.isNull("news_date")) {
                objProxy.realmSet$news_date(null);
            } else {
                objProxy.realmSet$news_date((String) json.getString("news_date"));
            }
        }
        if (json.has("news_image")) {
            if (json.isNull("news_image")) {
                objProxy.realmSet$news_image(null);
            } else {
                objProxy.realmSet$news_image((String) json.getString("news_image"));
            }
        }
        if (json.has("news_description")) {
            if (json.isNull("news_description")) {
                objProxy.realmSet$news_description(null);
            } else {
                objProxy.realmSet$news_description((String) json.getString("news_description"));
            }
        }
        if (json.has("video_url")) {
            if (json.isNull("video_url")) {
                objProxy.realmSet$video_url(null);
            } else {
                objProxy.realmSet$video_url((String) json.getString("video_url"));
            }
        }
        if (json.has("video_id")) {
            if (json.isNull("video_id")) {
                objProxy.realmSet$video_id(null);
            } else {
                objProxy.realmSet$video_id((String) json.getString("video_id"));
            }
        }
        if (json.has("content_type")) {
            if (json.isNull("content_type")) {
                objProxy.realmSet$content_type(null);
            } else {
                objProxy.realmSet$content_type((String) json.getString("content_type"));
            }
        }
        if (json.has("comments_count")) {
            if (json.isNull("comments_count")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'comments_count' to null.");
            } else {
                objProxy.realmSet$comments_count((long) json.getLong("comments_count"));
            }
        }
        if (json.has("added_date")) {
            if (json.isNull("added_date")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'added_date' to null.");
            } else {
                objProxy.realmSet$added_date((long) json.getLong("added_date"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.nevesti.www.bairroseguro.realm.table.NewsRealm createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.nevesti.www.bairroseguro.realm.table.NewsRealm obj = new com.nevesti.www.bairroseguro.realm.table.NewsRealm();
        final NewsRealmRealmProxyInterface objProxy = (NewsRealmRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("nid")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$nid((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'nid' to null.");
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("news_title")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$news_title((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$news_title(null);
                }
            } else if (name.equals("category_name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$category_name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$category_name(null);
                }
            } else if (name.equals("news_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$news_date((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$news_date(null);
                }
            } else if (name.equals("news_image")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$news_image((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$news_image(null);
                }
            } else if (name.equals("news_description")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$news_description((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$news_description(null);
                }
            } else if (name.equals("video_url")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$video_url((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$video_url(null);
                }
            } else if (name.equals("video_id")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$video_id((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$video_id(null);
                }
            } else if (name.equals("content_type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$content_type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$content_type(null);
                }
            } else if (name.equals("comments_count")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$comments_count((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'comments_count' to null.");
                }
            } else if (name.equals("added_date")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$added_date((long) reader.nextLong());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'added_date' to null.");
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'nid'.");
        }
        return realm.copyToRealm(obj);
    }

    public static com.nevesti.www.bairroseguro.realm.table.NewsRealm copyOrUpdate(Realm realm, com.nevesti.www.bairroseguro.realm.table.NewsRealm object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
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
            return (com.nevesti.www.bairroseguro.realm.table.NewsRealm) cachedRealmObject;
        }

        com.nevesti.www.bairroseguro.realm.table.NewsRealm realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
            NewsRealmColumnInfo columnInfo = (NewsRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
            long pkColumnIndex = columnInfo.nidIndex;
            long rowIndex = table.findFirstLong(pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class), false, Collections.<String> emptyList());
                    realmObject = new io.realm.NewsRealmRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, realmObject, object, cache) : copy(realm, object, update, cache);
    }

    public static com.nevesti.www.bairroseguro.realm.table.NewsRealm copy(Realm realm, com.nevesti.www.bairroseguro.realm.table.NewsRealm newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.nevesti.www.bairroseguro.realm.table.NewsRealm) cachedRealmObject;
        }

        // rejecting default values to avoid creating unexpected objects from RealmModel/RealmList fields.
        com.nevesti.www.bairroseguro.realm.table.NewsRealm realmObject = realm.createObjectInternal(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class, ((NewsRealmRealmProxyInterface) newObject).realmGet$nid(), false, Collections.<String>emptyList());
        cache.put(newObject, (RealmObjectProxy) realmObject);

        NewsRealmRealmProxyInterface realmObjectSource = (NewsRealmRealmProxyInterface) newObject;
        NewsRealmRealmProxyInterface realmObjectCopy = (NewsRealmRealmProxyInterface) realmObject;

        realmObjectCopy.realmSet$news_title(realmObjectSource.realmGet$news_title());
        realmObjectCopy.realmSet$category_name(realmObjectSource.realmGet$category_name());
        realmObjectCopy.realmSet$news_date(realmObjectSource.realmGet$news_date());
        realmObjectCopy.realmSet$news_image(realmObjectSource.realmGet$news_image());
        realmObjectCopy.realmSet$news_description(realmObjectSource.realmGet$news_description());
        realmObjectCopy.realmSet$video_url(realmObjectSource.realmGet$video_url());
        realmObjectCopy.realmSet$video_id(realmObjectSource.realmGet$video_id());
        realmObjectCopy.realmSet$content_type(realmObjectSource.realmGet$content_type());
        realmObjectCopy.realmSet$comments_count(realmObjectSource.realmGet$comments_count());
        realmObjectCopy.realmSet$added_date(realmObjectSource.realmGet$added_date());
        return realmObject;
    }

    public static long insert(Realm realm, com.nevesti.www.bairroseguro.realm.table.NewsRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long tableNativePtr = table.getNativePtr();
        NewsRealmColumnInfo columnInfo = (NewsRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long pkColumnIndex = columnInfo.nidIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((NewsRealmRealmProxyInterface) object).realmGet$nid();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$news_title = ((NewsRealmRealmProxyInterface) object).realmGet$news_title();
        if (realmGet$news_title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_titleIndex, rowIndex, realmGet$news_title, false);
        }
        String realmGet$category_name = ((NewsRealmRealmProxyInterface) object).realmGet$category_name();
        if (realmGet$category_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
        }
        String realmGet$news_date = ((NewsRealmRealmProxyInterface) object).realmGet$news_date();
        if (realmGet$news_date != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_dateIndex, rowIndex, realmGet$news_date, false);
        }
        String realmGet$news_image = ((NewsRealmRealmProxyInterface) object).realmGet$news_image();
        if (realmGet$news_image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_imageIndex, rowIndex, realmGet$news_image, false);
        }
        String realmGet$news_description = ((NewsRealmRealmProxyInterface) object).realmGet$news_description();
        if (realmGet$news_description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_descriptionIndex, rowIndex, realmGet$news_description, false);
        }
        String realmGet$video_url = ((NewsRealmRealmProxyInterface) object).realmGet$video_url();
        if (realmGet$video_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.video_urlIndex, rowIndex, realmGet$video_url, false);
        }
        String realmGet$video_id = ((NewsRealmRealmProxyInterface) object).realmGet$video_id();
        if (realmGet$video_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.video_idIndex, rowIndex, realmGet$video_id, false);
        }
        String realmGet$content_type = ((NewsRealmRealmProxyInterface) object).realmGet$content_type();
        if (realmGet$content_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.content_typeIndex, rowIndex, realmGet$content_type, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.comments_countIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$comments_count(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.added_dateIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$added_date(), false);
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long tableNativePtr = table.getNativePtr();
        NewsRealmColumnInfo columnInfo = (NewsRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long pkColumnIndex = columnInfo.nidIndex;
        com.nevesti.www.bairroseguro.realm.table.NewsRealm object = null;
        while (objects.hasNext()) {
            object = (com.nevesti.www.bairroseguro.realm.table.NewsRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((NewsRealmRealmProxyInterface) object).realmGet$nid();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$news_title = ((NewsRealmRealmProxyInterface) object).realmGet$news_title();
            if (realmGet$news_title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_titleIndex, rowIndex, realmGet$news_title, false);
            }
            String realmGet$category_name = ((NewsRealmRealmProxyInterface) object).realmGet$category_name();
            if (realmGet$category_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
            }
            String realmGet$news_date = ((NewsRealmRealmProxyInterface) object).realmGet$news_date();
            if (realmGet$news_date != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_dateIndex, rowIndex, realmGet$news_date, false);
            }
            String realmGet$news_image = ((NewsRealmRealmProxyInterface) object).realmGet$news_image();
            if (realmGet$news_image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_imageIndex, rowIndex, realmGet$news_image, false);
            }
            String realmGet$news_description = ((NewsRealmRealmProxyInterface) object).realmGet$news_description();
            if (realmGet$news_description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_descriptionIndex, rowIndex, realmGet$news_description, false);
            }
            String realmGet$video_url = ((NewsRealmRealmProxyInterface) object).realmGet$video_url();
            if (realmGet$video_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.video_urlIndex, rowIndex, realmGet$video_url, false);
            }
            String realmGet$video_id = ((NewsRealmRealmProxyInterface) object).realmGet$video_id();
            if (realmGet$video_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.video_idIndex, rowIndex, realmGet$video_id, false);
            }
            String realmGet$content_type = ((NewsRealmRealmProxyInterface) object).realmGet$content_type();
            if (realmGet$content_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.content_typeIndex, rowIndex, realmGet$content_type, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.comments_countIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$comments_count(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.added_dateIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$added_date(), false);
        }
    }

    public static long insertOrUpdate(Realm realm, com.nevesti.www.bairroseguro.realm.table.NewsRealm object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long tableNativePtr = table.getNativePtr();
        NewsRealmColumnInfo columnInfo = (NewsRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long pkColumnIndex = columnInfo.nidIndex;
        long rowIndex = Table.NO_MATCH;
        Object primaryKeyValue = ((NewsRealmRealmProxyInterface) object).realmGet$nid();
        if (primaryKeyValue != null) {
            rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
        }
        cache.put(object, rowIndex);
        String realmGet$news_title = ((NewsRealmRealmProxyInterface) object).realmGet$news_title();
        if (realmGet$news_title != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_titleIndex, rowIndex, realmGet$news_title, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.news_titleIndex, rowIndex, false);
        }
        String realmGet$category_name = ((NewsRealmRealmProxyInterface) object).realmGet$category_name();
        if (realmGet$category_name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.category_nameIndex, rowIndex, false);
        }
        String realmGet$news_date = ((NewsRealmRealmProxyInterface) object).realmGet$news_date();
        if (realmGet$news_date != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_dateIndex, rowIndex, realmGet$news_date, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.news_dateIndex, rowIndex, false);
        }
        String realmGet$news_image = ((NewsRealmRealmProxyInterface) object).realmGet$news_image();
        if (realmGet$news_image != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_imageIndex, rowIndex, realmGet$news_image, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.news_imageIndex, rowIndex, false);
        }
        String realmGet$news_description = ((NewsRealmRealmProxyInterface) object).realmGet$news_description();
        if (realmGet$news_description != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.news_descriptionIndex, rowIndex, realmGet$news_description, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.news_descriptionIndex, rowIndex, false);
        }
        String realmGet$video_url = ((NewsRealmRealmProxyInterface) object).realmGet$video_url();
        if (realmGet$video_url != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.video_urlIndex, rowIndex, realmGet$video_url, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.video_urlIndex, rowIndex, false);
        }
        String realmGet$video_id = ((NewsRealmRealmProxyInterface) object).realmGet$video_id();
        if (realmGet$video_id != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.video_idIndex, rowIndex, realmGet$video_id, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.video_idIndex, rowIndex, false);
        }
        String realmGet$content_type = ((NewsRealmRealmProxyInterface) object).realmGet$content_type();
        if (realmGet$content_type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.content_typeIndex, rowIndex, realmGet$content_type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.content_typeIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.comments_countIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$comments_count(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.added_dateIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$added_date(), false);
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long tableNativePtr = table.getNativePtr();
        NewsRealmColumnInfo columnInfo = (NewsRealmColumnInfo) realm.getSchema().getColumnInfo(com.nevesti.www.bairroseguro.realm.table.NewsRealm.class);
        long pkColumnIndex = columnInfo.nidIndex;
        com.nevesti.www.bairroseguro.realm.table.NewsRealm object = null;
        while (objects.hasNext()) {
            object = (com.nevesti.www.bairroseguro.realm.table.NewsRealm) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = Table.NO_MATCH;
            Object primaryKeyValue = ((NewsRealmRealmProxyInterface) object).realmGet$nid();
            if (primaryKeyValue != null) {
                rowIndex = Table.nativeFindFirstInt(tableNativePtr, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, ((NewsRealmRealmProxyInterface) object).realmGet$nid());
            }
            cache.put(object, rowIndex);
            String realmGet$news_title = ((NewsRealmRealmProxyInterface) object).realmGet$news_title();
            if (realmGet$news_title != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_titleIndex, rowIndex, realmGet$news_title, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.news_titleIndex, rowIndex, false);
            }
            String realmGet$category_name = ((NewsRealmRealmProxyInterface) object).realmGet$category_name();
            if (realmGet$category_name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.category_nameIndex, rowIndex, realmGet$category_name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.category_nameIndex, rowIndex, false);
            }
            String realmGet$news_date = ((NewsRealmRealmProxyInterface) object).realmGet$news_date();
            if (realmGet$news_date != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_dateIndex, rowIndex, realmGet$news_date, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.news_dateIndex, rowIndex, false);
            }
            String realmGet$news_image = ((NewsRealmRealmProxyInterface) object).realmGet$news_image();
            if (realmGet$news_image != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_imageIndex, rowIndex, realmGet$news_image, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.news_imageIndex, rowIndex, false);
            }
            String realmGet$news_description = ((NewsRealmRealmProxyInterface) object).realmGet$news_description();
            if (realmGet$news_description != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.news_descriptionIndex, rowIndex, realmGet$news_description, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.news_descriptionIndex, rowIndex, false);
            }
            String realmGet$video_url = ((NewsRealmRealmProxyInterface) object).realmGet$video_url();
            if (realmGet$video_url != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.video_urlIndex, rowIndex, realmGet$video_url, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.video_urlIndex, rowIndex, false);
            }
            String realmGet$video_id = ((NewsRealmRealmProxyInterface) object).realmGet$video_id();
            if (realmGet$video_id != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.video_idIndex, rowIndex, realmGet$video_id, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.video_idIndex, rowIndex, false);
            }
            String realmGet$content_type = ((NewsRealmRealmProxyInterface) object).realmGet$content_type();
            if (realmGet$content_type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.content_typeIndex, rowIndex, realmGet$content_type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.content_typeIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.comments_countIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$comments_count(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.added_dateIndex, rowIndex, ((NewsRealmRealmProxyInterface) object).realmGet$added_date(), false);
        }
    }

    public static com.nevesti.www.bairroseguro.realm.table.NewsRealm createDetachedCopy(com.nevesti.www.bairroseguro.realm.table.NewsRealm realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.nevesti.www.bairroseguro.realm.table.NewsRealm unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.nevesti.www.bairroseguro.realm.table.NewsRealm();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.nevesti.www.bairroseguro.realm.table.NewsRealm) cachedObject.object;
            }
            unmanagedObject = (com.nevesti.www.bairroseguro.realm.table.NewsRealm) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        NewsRealmRealmProxyInterface unmanagedCopy = (NewsRealmRealmProxyInterface) unmanagedObject;
        NewsRealmRealmProxyInterface realmSource = (NewsRealmRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$nid(realmSource.realmGet$nid());
        unmanagedCopy.realmSet$news_title(realmSource.realmGet$news_title());
        unmanagedCopy.realmSet$category_name(realmSource.realmGet$category_name());
        unmanagedCopy.realmSet$news_date(realmSource.realmGet$news_date());
        unmanagedCopy.realmSet$news_image(realmSource.realmGet$news_image());
        unmanagedCopy.realmSet$news_description(realmSource.realmGet$news_description());
        unmanagedCopy.realmSet$video_url(realmSource.realmGet$video_url());
        unmanagedCopy.realmSet$video_id(realmSource.realmGet$video_id());
        unmanagedCopy.realmSet$content_type(realmSource.realmGet$content_type());
        unmanagedCopy.realmSet$comments_count(realmSource.realmGet$comments_count());
        unmanagedCopy.realmSet$added_date(realmSource.realmGet$added_date());

        return unmanagedObject;
    }

    static com.nevesti.www.bairroseguro.realm.table.NewsRealm update(Realm realm, com.nevesti.www.bairroseguro.realm.table.NewsRealm realmObject, com.nevesti.www.bairroseguro.realm.table.NewsRealm newObject, Map<RealmModel, RealmObjectProxy> cache) {
        NewsRealmRealmProxyInterface realmObjectTarget = (NewsRealmRealmProxyInterface) realmObject;
        NewsRealmRealmProxyInterface realmObjectSource = (NewsRealmRealmProxyInterface) newObject;
        realmObjectTarget.realmSet$news_title(realmObjectSource.realmGet$news_title());
        realmObjectTarget.realmSet$category_name(realmObjectSource.realmGet$category_name());
        realmObjectTarget.realmSet$news_date(realmObjectSource.realmGet$news_date());
        realmObjectTarget.realmSet$news_image(realmObjectSource.realmGet$news_image());
        realmObjectTarget.realmSet$news_description(realmObjectSource.realmGet$news_description());
        realmObjectTarget.realmSet$video_url(realmObjectSource.realmGet$video_url());
        realmObjectTarget.realmSet$video_id(realmObjectSource.realmGet$video_id());
        realmObjectTarget.realmSet$content_type(realmObjectSource.realmGet$content_type());
        realmObjectTarget.realmSet$comments_count(realmObjectSource.realmGet$comments_count());
        realmObjectTarget.realmSet$added_date(realmObjectSource.realmGet$added_date());
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("NewsRealm = proxy[");
        stringBuilder.append("{nid:");
        stringBuilder.append(realmGet$nid());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{news_title:");
        stringBuilder.append(realmGet$news_title() != null ? realmGet$news_title() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{category_name:");
        stringBuilder.append(realmGet$category_name() != null ? realmGet$category_name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{news_date:");
        stringBuilder.append(realmGet$news_date() != null ? realmGet$news_date() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{news_image:");
        stringBuilder.append(realmGet$news_image() != null ? realmGet$news_image() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{news_description:");
        stringBuilder.append(realmGet$news_description() != null ? realmGet$news_description() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{video_url:");
        stringBuilder.append(realmGet$video_url() != null ? realmGet$video_url() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{video_id:");
        stringBuilder.append(realmGet$video_id() != null ? realmGet$video_id() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{content_type:");
        stringBuilder.append(realmGet$content_type() != null ? realmGet$content_type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{comments_count:");
        stringBuilder.append(realmGet$comments_count());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{added_date:");
        stringBuilder.append(realmGet$added_date());
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
        NewsRealmRealmProxy aNewsRealm = (NewsRealmRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aNewsRealm.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aNewsRealm.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aNewsRealm.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
