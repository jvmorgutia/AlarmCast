package alarmcast.app.receiver;

import android.net.Uri;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by chuck on 1/9/15.
 */
public class CastJson {

    public static class UriSerializer implements JsonSerializer<Uri> {
        @Override
        public JsonElement serialize(Uri src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        public static class UriDeserializer implements JsonDeserializer<Uri> {
            @Override
            public Uri deserialize(final JsonElement src, final Type srcType, final JsonDeserializationContext context) throws JsonParseException {
                return Uri.parse(src.getAsString());
            }
        }
    }
}
