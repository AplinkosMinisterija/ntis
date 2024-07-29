# Spark Middlelayer API

# Change log

spark-dao-gen change log.
Each version description can contain following sections:
- Added
- Changed
- Removed
- Fixed

## [1.6.1] - 2022-10-20
### Fixed
- SAP-242 - system error when user does not exsit during login
- minor fixes

## [1.6.0] - 2022-10-20
- ViispAuthService added
- AppData - springProfilesActive property added
- Breaking change: SprExternalAuthorization is no longer @Component, it is required to define bean in project Spring config

## [1.5.1] - 2022-10-12
SprListIdKeyValue type added

## [1.5.0] - 2022-10-12
Unified spark-core versioning

## [0.2.0] - 2022-10-06
Removed generated DAO service classes - from now on these classed are external.
Introduced interfaces for DAO classes in standard Admin, Tasks and Common modules. Projects can use any implementation of DB services

## [0.0.1] - 2022-09-27
Initial version
