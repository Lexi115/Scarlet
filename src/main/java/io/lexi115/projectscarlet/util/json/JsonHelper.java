package io.lexi115.projectscarlet.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Helper class to handle JSON object parsing.
 *
 * @author Lexi115
 * @since 1.0
 */
@Component
@AllArgsConstructor
public class JsonHelper {

    /**
     * The object mapper.
     */
    private final ObjectMapper objectMapper;

    /**
     * Converts an object into a JSON string.
     *
     * @param object The target object.
     * @return A JSON string representation of the object.
     * @throws JsonException If conversion fails.
     * @since 1.0
     */
    public String stringify(final Object object) throws JsonException {
        try {
            return objectMapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonException(e.getMessage());
        }
    }

    /**
     * Converts a JSON string into an object.
     *
     * @param json The JSON string to parse.
     * @param type The target class type.
     * @param <T>  The class type.
     * @return The parsed object.
     * @throws JsonException If conversion fails.
     * @since 1.0
     */
    public <T> T parse(final String json, final Class<T> type) throws JsonException {
        try {
            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new JsonException(e.getMessage());
        }
    }

    /**
     * Converts a JSON string into a list.
     *
     * @param json        The JSON string to parse.
     * @param elementType The type of the elements in the list.
     * @param <T>         The class type.
     * @return The parsed object.
     * @throws JsonException If conversion fails.
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> parseList(final String json, final Class<T> elementType) throws JsonException {
        return parseCollection(json, (Class<List<T>>) (Class<?>) List.class, elementType);
    }

    /**
     * Converts a JSON string into a collection.
     *
     * @param json           The JSON string to parse.
     * @param collectionType The collection type.
     * @param elementType    The type of the elements in the collection.
     * @param <T>            The class type.
     * @param <C>            The collection type.
     * @return The parsed object.
     * @throws JsonException If conversion fails.
     * @since 1.0
     */
    public <T, C extends Collection<T>> C parseCollection(
            final String json,
            final Class<C> collectionType,
            final Class<T> elementType
    ) throws JsonException {
        try {
            return objectMapper.readValue(
                    json,
                    TypeFactory.defaultInstance().constructCollectionType(collectionType, elementType)
            );
        } catch (IOException e) {
            throw new JsonException(e.getMessage());
        }
    }

}
