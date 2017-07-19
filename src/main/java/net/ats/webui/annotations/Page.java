package net.ats.webui.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.ats.webui.enums.Browser;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Page {
	public String pages() default "";
	public Browser browser() default Browser.Chrome;
	public int timeOut() default 30;
}
