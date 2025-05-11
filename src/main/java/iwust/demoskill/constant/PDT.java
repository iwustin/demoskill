package iwust.demoskill.constant;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 *  Shorten variant of PersistentDataType
 *  Адаптировано под 1.19.3 и demoskill (нет BOOLEAN)
 *  */
public final class PDT {
    @Nonnull
    public static final PersistentDataType<Byte, Byte> BYTE = PersistentDataType.BYTE;
    @Nonnull
    public static final PersistentDataType<byte[], byte[]> BYTE_ARRAY = PersistentDataType.BYTE_ARRAY;
    @Nonnull
    public static final PersistentDataType<Short, Short> SHORT = PersistentDataType.SHORT;
    @Nonnull
    public static final PersistentDataType<Integer, Integer> INTEGER = PersistentDataType.INTEGER;
    @Nonnull
    public static final PersistentDataType<int[], int[]> INTEGER_ARRAY = PersistentDataType.INTEGER_ARRAY;
    @Nonnull
    public static final PersistentDataType<Long, Long> LONG = PersistentDataType.LONG;
    @Nonnull
    public static final PersistentDataType<long[], long[]> LONG_ARRAY = PersistentDataType.LONG_ARRAY;
    @Nonnull
    public static final PersistentDataType<Float, Float> FLOAT = PersistentDataType.FLOAT;
    @Nonnull
    public static final PersistentDataType<Double, Double> DOUBLE = PersistentDataType.DOUBLE;
    @Nonnull
    public static final PersistentDataType<String, String> STRING = PersistentDataType.STRING;
    @Nonnull
    public static final PersistentDataType<PersistentDataContainer, PersistentDataContainer> CONTAINER = PersistentDataType.TAG_CONTAINER;
}