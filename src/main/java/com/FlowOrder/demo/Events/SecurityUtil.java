package com.FlowOrder.demo.Events;

import com.FlowOrder.demo.Entity.Users;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    private SecurityUtil() {}
    public static Integer getCurrentUserId(){
        var auth= SecurityContextHolder.getContext().getAuthentication();
        Users user=(Users) auth.getPrincipal();
        return user.getId();
    }
}
