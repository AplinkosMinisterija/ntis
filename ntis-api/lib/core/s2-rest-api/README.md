# Spark rest API

# Change Log

spark-rest-api change log.
Each version description can contain following sections:
- Added
- Changed
- Removed
- Fixed

## [1.8.4] - 2023-10-09
### Added
- validation error handlers were added to S2RestApiErrorHandler
### Fixed
- stack trace return on error bug fix in S2RestApiErrorHandler
### Removed
- removed app.serverErrror.visibility.forUser parameter. Full stack trace is now returned when there are test or dev Spring profiles active.


## [1.4.1] - 2022-10-19
### Fixed
- ErrorHandler NullPointer bug when there was no stack trace

## [1.4.0] - 2022-09-30
### Changed
- POM groupId changed to eu.itreegroup.spark
- POM dependencies cleanup

## [1.3.0] - 2022-09-08
### Added
- orgId and orgName added to WebSessionInfo
- multiorganisation support added
### Changed
- role refactored to roleCode in WebSessionInfo

## [1.2.0] - 20122-07-29
### Added
- SparkRuntimeException
### Changed
- Error handling to allow throwing SparkRuntimeException 
- Jackson library version changed to 2.13.3

## [1.1.1] - 2022-09-22
Specific version for eNotary project
### Removed
- ApplicationMailer
- java.mail dependency
## [1.1.0] - 2022-06-13
### Added
- roleId added to WebSessionInfo
### Changed
- Jackson library version changed to 2.12.6
## [1.0.0] - 2022-06-10
Initial version
 