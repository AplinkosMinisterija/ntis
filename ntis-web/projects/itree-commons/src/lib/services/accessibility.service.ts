import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AccessibilityCssClass, AccessibilityCssClasses, AccessibilityFontSizeCssClass } from '../types/accessibility';

@Injectable({
  providedIn: 'root',
})
export class AccessibilityService {
  storage: Storage = localStorage;
  // Naudojant localStorage api LOCAL_STORAGE_KEY yra key užsetinti/gauti aktyvius css stilius (this.storage.setItem(this.LOCAL_STORAGE_KEY, aktyvių-css-stilių-masyvas))
  private readonly LOCAL_STORAGE_KEY = 'accessibility-styles';

  public readonly styles$ = new BehaviorSubject<AccessibilityCssClass[]>(this.getLocalStorageData());

  // Sudedamos šiuo metu aktyvios css klasės
  private _stylesContainer: AccessibilityCssClass[] = this.styles$.value;

  // Css klasių konteineris, kuriame sudėtos css klasės iš app/accessibility/styles.scss
  cssClasses: AccessibilityCssClasses = {
    fs1: 'increase-font-size-1',
    fs2: 'increase-font-size-2',
    fs3: 'increase-font-size-3',
    boldLinks: 'bold-links',
    cursor: 'cursor',
    contrast: 'contrast',
  };

  get styles(): AccessibilityCssClass[] {
    return this.styles$.value;
  }

  set styles(styles: AccessibilityCssClass[]) {
    this.styles$.next(styles);
    this.setLocalStorageData(styles);
  }

  private addCssClass(classToAdd: AccessibilityCssClass): void {
    this._stylesContainer.push(classToAdd);
    this.styles = [...this.styles, classToAdd];
  }

  private removeCssClass(classToRemove: AccessibilityCssClass): void {
    this.styles = this.styles.filter((cssClass) => cssClass !== classToRemove);
  }

  resetCssClasses(): void {
    this.styles = [];
  }

  // Padidinti tekstą, viso yra numatyti trys dydžiai, atitinkamai tam yra sukurtos trys css klasės
  setTextSize(sizeLevel: number): void {
    this.removeTextSizeIncreasement();
    const { fs1, fs2, fs3 } = this.cssClasses;
    switch (sizeLevel) {
      case 1: {
        this.fillFontSizeStyle(<AccessibilityFontSizeCssClass>fs1);
        break;
      }
      case 2: {
        this.fillFontSizeStyle(<AccessibilityFontSizeCssClass>fs2);
        break;
      }
      case 3: {
        this.fillFontSizeStyle(<AccessibilityFontSizeCssClass>fs3);
        break;
      }
    }
  }

  removeTextSizeIncreasement(): void {
    this.styles = this.styles.filter((fs) => !fs.startsWith('increase-font-size-'));
  }

  getTextSizeLevel(): number {
    const { fs1, fs2, fs3 } = this.cssClasses;
    const classesArray = [fs1, fs2, fs3];
    return classesArray.findIndex((fontClass) => this.styles.some((cssClass) => cssClass === fontClass)) + 1;
  }

  boldLinks(): void {
    this.addCssClass(this.cssClasses.boldLinks);
  }

  removeBoldedLinks(): void {
    this.removeCssClass(this.cssClasses.boldLinks);
  }

  increaseMouseCursorSize(): void {
    this.addCssClass(this.cssClasses.cursor);
  }

  removeMouseCursorSizeIncreasement(): void {
    this.removeCssClass(this.cssClasses.cursor);
  }

  changeContrast(): void {
    this.addCssClass(this.cssClasses.contrast);
  }

  removeChangedContrast(): void {
    this.removeCssClass(this.cssClasses.contrast);
  }

  private fillFontSizeStyle(cssStyle: AccessibilityFontSizeCssClass): void {
    this.removeTextSizeIncreasement();
    this.addCssClass(cssStyle);
  }

  private setLocalStorageData(accessibilityClasses: AccessibilityCssClass[]): void {
    this.storage.setItem(this.LOCAL_STORAGE_KEY, JSON.stringify(accessibilityClasses));
  }

  private getLocalStorageData(): AccessibilityCssClass[] {
    const lsStyles = this.storage.getItem(this.LOCAL_STORAGE_KEY);
    return lsStyles ? (JSON.parse(lsStyles) as AccessibilityCssClass[]) : [];
  }
}
