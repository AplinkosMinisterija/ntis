export enum ExtendedSearchCondition {
  More = '>',
  Less = '<',
  Equals = '=',
  NotEqual = '!=',
  StartsWith = '%-',
  EndsWith = '-%',
  Contains = '%-%',
  Empty = 'null',
  NotEmpty = 'notnull',
  Period = '[]',
}

export enum ExtendedSearchUpperLower {
  Regular = 'regular',
  Lowercase = 'lowercase',
  Uppercase = 'uppercase',
  CaseInsensitiveLatin = 'caseInsensitiveLatin',
  RegularLatin = 'regularLatin',
}
