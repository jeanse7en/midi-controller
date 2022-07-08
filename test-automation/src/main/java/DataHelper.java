import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DataHelper {
    private static final int MAX_TOKEN_WATERMARK_LENGTH = 50;
    private static final ObjectMapper gson = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    private DataHelper() {
    }

    public static boolean hasData(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean hasData(Object obj) {
        return obj != null || ((obj instanceof String) && !((String) obj).isEmpty());
    }
    public static <T> String toJson(T data, Function<T, ?>... functions) {
        if (data == null || functions == null || functions.length < 1) {
            return null;
        }
        Map<String, String> hashMap = buildMap(data, functions);
        return toJson(hashMap);
    }

    private static <T> Map<String, String> buildMap(T data, Function<T, ?>[] functions) {
        Map<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < functions.length; i = i + 2) {
            Function<T, ?> function = functions[i];
            Function<T, ?> function2 = functions[i + 1];
            if (function.apply(data) == null) {
                continue;
            }
            hashMap.put(function.apply(data).toString(), function2.apply(data).toString());
        }
        return hashMap;
    }



    public static List<Integer> asInteger(String value, String regex) {
        List<Integer> numbers = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            for (String s : value.split(regex)) {
                numbers.add(Integer.valueOf(s));
            }
        }
        return numbers;
    }

    public static List<String> asString(String value, String regex) {
        List<String> sValues = new ArrayList<>();
        if (value != null && !value.isEmpty()) {
            for (String s : value.split(regex)) {
                if (hasData(s.trim())) {
                    sValues.add(s);
                }
            }
        }
        return sValues;
    }



    /**
    /**
     * mergeStringListToString
     */
    public static String mergeStringListToString(List<String> stringList, String delimiter) {
        if (CollectionUtils.isEmpty(stringList) || StringUtils.isBlank(delimiter)) {
            return null;
        }
        return stringList.stream().filter(Objects::nonNull).collect(Collectors.joining(delimiter));
    }

    /**
     * watermark the token
     *
     * @param token
     * @return String
     */
    public static String watermarkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return token;
        }
        String[] split = token.split("\\.");
        if (split == null || split.length <= 0) {
            return getSubstring(token);
        }
        return String.format("%s-xxxx%sxx x-%s", getSubstring(split[0]),
                getSubstring(split[1]), getSubstring(split[split.length - 1]));
    }

    private static String getSubstring(String text) {
        return text.substring(Math.min(text.length(), MAX_TOKEN_WATERMARK_LENGTH)) + "xxx";
    }

    public static <T, K> List<K> getFieldFromList(Collection<T> data, Function<? super T, ? extends K> var0) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        return data.stream().map(var0).collect(Collectors.toList());
    }

    public static <T, K> K getFirstFromList(Collection<T> data, Function<? super T, ? extends K> var0) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.stream().filter(Objects::nonNull).findAny().map(var0).orElse(null);
    }

    public static <T, K, U> Map<K, U> buildMapFromList(List<T> data, Function<? super T, ? extends K> var0, Function<? super T, ? extends U> var1) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.toMap(var0, var1, (u, v) -> v, HashMap::new));
    }

    public static <T, K, U> Map<K, List<U>> buildMapGroup(List<T> data, Function<? super T, ? extends K> var0, Function<? super T, ? extends U> var1) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return  data.stream().collect(Collectors.groupingBy(var0, Collectors.mapping(var1, Collectors.toList())));
    }

    public static <T, K, U> Map<K, Set<U>> buildMapGroupDistinct(List<T> data, Function<? super T, ? extends K> var0, Function<? super T, ? extends U> var1) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return  data.stream().collect(Collectors.groupingBy(var0, Collectors.mapping(var1, Collectors.toSet())));
    }


    public static <T, K, U> Map<K, U> buildMapFromList(Collection<T> data, Function<? super T, ? extends K> var0,
                                                       Function<? super T, ? extends U> var1, Predicate<? super T> fil0) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return data.stream().filter(fil0).collect(Collectors.toMap(var0, var1, (u, v) -> u, HashMap::new));
    }

    public static <T, K, U> Map<K, List<U>> buildMapGroupFromList(Collection<T> data, Function<? super T, ? extends K> var0,
                                                       Function<? super T, ? extends U> var10) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashMap<>();
        }
        return data.stream().collect(Collectors.groupingBy(var0, Collectors.mapping(var10, Collectors.toList())));
    }

    public static <K, V, X> X getValueInMap(final Map<? super K, V> map, final K key, Function<? super V, ? extends X> var0) {
        V object = MapUtils.getObject(map, key);
        return Optional.ofNullable(object).map(var0).orElse(null);
    }


    public static <K, V, X, T extends Exception> X getValueInMap(final Map<? super K, V> map, final K key, Function<? super V, ? extends X> var0, Supplier<T> supplier) throws Exception {
        V object = MapUtils.getObject(map, key);
        return Optional.ofNullable(object).map(var0).orElseThrow(supplier);
    }


    public static <T, K> List<K> getFieldList(Collection<T> data, Function<? super T, ? extends K> var0) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        return data.stream().map(var0).collect(Collectors.toList());
    }

    public static <T, K> Set<K> getFieldSet(Collection<T> data, Function<? super T, ? extends K> var0) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashSet<>();
        }
        return data.stream().map(var0).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static <T, K> Set<K> getFieldSet(Collection<T> data,  Predicate<? super T> fil0, Function<? super T, ? extends K> var0) {
        if (CollectionUtils.isEmpty(data)) {
            return new HashSet<>();
        }
        Set<K> collect = data.stream().filter(fil0).map(var0).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(collect)) {
            return new HashSet<>();
        }
        return collect;
    }

    public static <T, K> List<K> getFieldList(Collection<T> data, Function<? super T, ? extends K> var0, Predicate<? super T> fil0) {
        if (CollectionUtils.isEmpty(data)) {
            return new ArrayList<>();
        }
        List<K> collect = data.stream().filter(fil0).map(var0).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            return new ArrayList<>();
        }
        return collect;
    }

    public static String getString(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value;
    }


    @SafeVarargs
    public static <T> void removeDuplicatesFromList(List<T> list, Function<T, ?>... keyFunctions) {
        Set<List<?>> set = new HashSet<>();
        ListIterator<T> iter = list.listIterator();
        while (iter.hasNext()) {
            T element = iter.next();
            List<?> functionResults = Arrays.stream(keyFunctions)
                    .map(function -> function.apply(element))
                    .collect(Collectors.toList());
            if (!set.add(functionResults)) {
                iter.remove();
            }
        }
    }

    @SafeVarargs
    public static <T> List<T> getDuplicatesFromList(List<T> list, Function<T, ?>... keyFunctions) {
        Set<List<?>> set = new HashSet<>();
        ListIterator<T> iter = list.listIterator();
        List<T> results = new ArrayList<>();
        while (iter.hasNext()) {
            T element = iter.next();
            List<?> functionResults = Arrays.stream(keyFunctions)
                    .map(function -> function.apply(element))
                    .collect(Collectors.toList());
            if (set.add(functionResults)) {
                continue;
            }
            if (!results.contains(element)) {
                results.add(element);
            }
        }
        return results;
    }

    @SafeVarargs
    public static <T> List<T> getListWithoutDuplicates(List<T> list, Function<T, ?>... keyFunctions) {

        List<T> result = new ArrayList<>();

        Set<List<?>> set = new HashSet<>();

        for (T element : list) {
            List<?> functionResults = Arrays.stream(keyFunctions)
                    .map(function -> function.apply(element))
                    .collect(Collectors.toList());

            if (set.add(functionResults)) {
                result.add(element);
            }
        }

        return result;
    }

    public static <T, K> Integer getValueAsInteger(T data, Function<? super T, ? extends K> var0) {
        if (data == null) {
            return null;
        }

        K apply = var0.apply(data);
        if (apply == null ||  !StringUtils.isNumeric(apply.toString())) {
            return null;
        }
        return Integer.valueOf(apply.toString());

    }
}
