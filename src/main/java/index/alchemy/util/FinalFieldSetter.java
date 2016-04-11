package index.alchemy.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class FinalFieldSetter {

    private static final FinalFieldSetter INSTANCE;

    static {
        try {
            INSTANCE = new FinalFieldSetter();
        } catch (final ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final Object unsafeObj;

    private final Method putObjectMethod;

    private final Method objectFieldOffsetMethod;

    private final Method staticFieldOffsetMethod;

    private final Method staticFieldBaseMethod;

    private FinalFieldSetter() throws ReflectiveOperationException {

        final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");

        final Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);

        this.unsafeObj = unsafeField.get(null);

        this.putObjectMethod = unsafeClass.getMethod("putObject", Object.class,
            long.class, Object.class);
        this.objectFieldOffsetMethod = unsafeClass.getMethod("objectFieldOffset",
            Field.class);
        this.staticFieldOffsetMethod = unsafeClass.getMethod("staticFieldOffset",
            Field.class);
        this.staticFieldBaseMethod = unsafeClass.getMethod("staticFieldBase",
            Field.class);
    }

    public static FinalFieldSetter getInstance() {
        return INSTANCE;
    }

    public void set(final Object o, final Field field, final Object value) throws Exception {

        final Object fieldBase = o;
        final long fieldOffset = (Long) this.objectFieldOffsetMethod.invoke(
            this.unsafeObj, field);

        this.putObjectMethod.invoke(this.unsafeObj, fieldBase, fieldOffset, value);
    }

    public void setStatic(final Field field, final Object value) throws Exception {

        final Object fieldBase = this.staticFieldBaseMethod.invoke(this.unsafeObj, field);
        final long fieldOffset = (Long) this.staticFieldOffsetMethod.invoke(
            this.unsafeObj, field);

        this.putObjectMethod.invoke(this.unsafeObj, fieldBase, fieldOffset, value);
    }
}