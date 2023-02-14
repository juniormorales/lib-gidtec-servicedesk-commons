package com.pe.gidtec.servicedesk.lib.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER})
public @interface HttpHeadersMapping {
}
