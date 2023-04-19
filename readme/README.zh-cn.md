# learn-spring-security

[![](https://img.shields.io/badge/JDK-8-green.svg)](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

## 详细

### Authentication

`Authentication` (认证/鉴权) 会在请求时被放入 `SecurityContext` 中以供其他需要认证的地方使用。`SecurityContext`  被 `SecurityContextHolder` 托管，默认策略下，`SecurityContextHolder`  对`SecurityContext`的控制是 `ThreadLocal` 的，一个线程中有且仅有一个`SecurityContext`对象。在请求结束后，`SecurityContextHolder`  中的 `SecurityContext` 会被清除，以避免在多个请求之间发生混淆或内存泄漏。因此，`SecurityContextHolder` 中存储的 `SecurityContext` 对象的生命周期只在每个请求内部有效，并且是线程安全的。

### Authority 与 Role

- Authority（权限）是指对某个资源进行特定操作的权力，例如读取、写入、更新或删除。在一个应用程序中，可以定义多个权限来表示所有可能的操作。通常，权限是由系统管理员分配给用户或角色的，以便限制用户对系统中的资源的访问。
- Role（角色）是一组权限的集合，表示系统中的特定功能或责任。例如，一个电子商务系统可能会定义“管理员”、“销售代表”和“客户”等角色。每个角色都可以有一个或多个权限，具体取决于角色在系统中的责任。在实际应用中，用户通常被分配到角色，而不是直接分配到权限。

因此，可以将权限看作是系统中的操作，而角色则是将这些操作组合在一起的逻辑集合。当用户被分配到一个角色时，他们会自动具有该角色中定义的所有权限。这样，可以更容易地管理用户的访问权限，并在需要时快速更改这些权限。

在 Spring Security 设计中，Role 属于 Authority，都为字符串。Spring Security 在设计时考虑到了权限管理的灵活性和可扩展性，不同的应用系统可能有不同的角色和权限，为了防止权限字符串之间的混淆和重复，设计了 Authority 的前缀来区分不同应用系统的角色和权限。

特别的，Role 默认带有 ROLE_ 前缀，开发者可以通过配置 [`GrantedAuthorityDefaults`](https://github.com/spring-projects/spring-security/blob/main/config/src/main/java/org/springframework/security/config/core/GrantedAuthorityDefaults.java)  来更改默认前缀<sup>[1]</sup>。

开发时，为每个用户分配他们的 Role 而不是分配 Authority，Spring Security 提供了 [Role hierarchy](https://en.wikipedia.org/wiki/Role_hierarchy) 的默认实现 [`RoleHierarchyImpl`](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/access/hierarchicalroles/RoleHierarchyImpl.java)，请使用 [`RoleHierarchyImpl`](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/access/hierarchicalroles/RoleHierarchyImpl.java) 控制 Role 和 Authority。在需要 Authentication 判断是否具有 Authority 时请勿忘记通过 [`RoleHierarchy.getReachableGrantedAuthorities`](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/access/hierarchicalroles/RoleHierarchy.java) 获取当前用户的所有 Authority，否则用户的 Authority 将仅包含 Role。

## 另请参阅

[Role-based access control](https://en.wikipedia.org/wiki/Role-based_access_control)

[Role hierarchy](https://en.wikipedia.org/wiki/Role_hierarchy)

[JSON Web Token](https://en.wikipedia.org/wiki/JSON_Web_Token)

[Salt (cryptography)](https://en.wikipedia.org/wiki/Salt_(cryptography))

[Rainbow table](https://en.wikipedia.org/wiki/Rainbow_table)

[Spring Security and Shiro Google Trends](https://trends.google.com/trends/explore?cat=5&date=today%205-y&q=Spring%20Security,Shiro&hl=en)

[Session fixation](https://en.wikipedia.org/wiki/Session_fixation)

[Difference between Role and GrantedAuthority in Spring Security](https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security)

## 脚注
[1] 另请参阅 [AuthorityAuthorizationManager](https://github.com/spring-projects/spring-security/blob/main/core/src/main/java/org/springframework/security/authorization/AuthorityAuthorizationManager.java) 和 [GrantedAuthorityDefaults](https://github.com/spring-projects/spring-security/blob/main/config/src/main/java/org/springframework/security/config/core/GrantedAuthorityDefaults.java)