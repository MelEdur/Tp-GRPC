package com.unla.tp.util;

import io.grpc.Context;

public class ContextKeys {
    public static final Context.Key<String> ROLE_CONTENT_KEY = Context.key("rol");
    public static final Context.Key<String> USER_CONTENT_KEY = Context.key("usuario");
}
