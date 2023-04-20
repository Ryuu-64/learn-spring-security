package org.ryuu.learn.spring.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyUtils;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(value = "security.role")
@Data
public class RoleHierarchyConfig {
    private final Map<String, List<String>> defaultRoleHierarchyMap;

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl defaultRoleHierarchy = new RoleHierarchyImpl();
        String hierarchy = RoleHierarchyUtils.roleHierarchyFromMap(defaultRoleHierarchyMap);
        defaultRoleHierarchy.setHierarchy(hierarchy);
        return defaultRoleHierarchy;
    }
}
