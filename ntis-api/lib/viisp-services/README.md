# VIISP services

# Change Log

viisp-services change log.
Each version description can contain following sections:
- Added
- Changed
- Removed
- Fixed

## [1.5.0]
### Changed
- Java17
- migrate from javax.xml.bind to jakarta.xml.bin

## [1.4.1]
### Removed 
- Logging implementations, only api is used

## [1.4.0]
- BREAKING CHANGE: Using Spring Security version 6.

## [1.3.0]
### Changed 
- BREAKING CHANGE: Keystore and certificate are provided through File type parameter. It gives more flexibility for different environments (e.g. Spring Boot). Migration to this version: change keystore and certificate parameters to File: keyStoreFile, viispSignCertificateFile. When using Spring it is enough to just change property names.

### Removed
- Obsolete and unused classes.


## [1.2.1]
### Changed 
- Signature validation adapted for jre17. Jre 17 has secureValidation enabled by default. VIISP signature uses old RSA-SHA1, so secureValidation is disabled now. It is possible to enable secure signature validation by passing secureValidation = true parameter in any config implementation derived from BaseCertConfig. 

## [1.2.0]
### Changed 
- BREAKING CHANGE: ViispAuthData customData type changed from Map<String, String> to Map<String, Object> for better flexibility.
- customData serialization/deserialization to/from string only supports String objects.

## [1.1.1]
### Changed 
- BREAKING CHANGE: ViispAuth Client API's changes. SAP-314 - Sukurti galimybę perduoti papildomus parametrus prisijungimo metu. https://jira.itreegroup.eu/browse/SAP-314

## [1.1.0]
### Changed 
- BREAKING CHANGE: return type of initAuth() method changed from string to ViispAuthResult. According to https://jira.itreegroup.eu/browse/SAP-253

## [1.0.1]
### Added 
- required runtime jax-b dependecies

## [1.0.0]
Initial version
 