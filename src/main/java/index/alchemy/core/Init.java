package index.alchemy.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraftforge.fml.common.LoaderState.ModState;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Init {
	
	public ModState state() default ModState.UNLOADED;
	
}
