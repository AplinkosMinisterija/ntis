export type AccessibilityFontSizeCssClass =
  | 'increase-font-size-1'
  | 'increase-font-size-2'
  | 'increase-font-size-3'
  | '';

export type AccessibilityCssClass = AccessibilityFontSizeCssClass | 'bold-links' | 'cursor' | 'contrast';

export interface AccessibilityCssClasses {
  fs1?: AccessibilityCssClass;
  fs2?: AccessibilityCssClass;
  fs3?: AccessibilityCssClass;
  boldLinks?: AccessibilityCssClass;
  cursor?: AccessibilityCssClass;
  contrast?: AccessibilityCssClass;
}
