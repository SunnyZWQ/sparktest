
## 1 概述

keystone是Nova中的一个服务。用于给OpenStack内部组件提供安全验证服务。



## 2 keystone架构

### Service

Keystone被组织为在一个或多个端点上公开的一组内部服务。前端工作时会结合多个服务。

例如：一个验证服务会通过Identity Service来验证user/project身份，成功之后，通过Token Service创建并返回一个Token（令牌）。

服务包括：
- 身份服务：对users和groups提供身份验证服务。在基础情况下，数据由Identity Service管理，允许对这些数据提供CRUD服务。在更复杂的情况下，数据由authoritative backend service管理，例如Identity Service作为LDAP的前端，在这种情况下，LDAP服务器提供验证信息的真假，这时Identity Service的角色是准确的延迟信息处理（交给LDAP）。
- 资源服务：提供数据
- 分配服务
- 令牌服务
- 目录服务：为终端提供目录服务














































