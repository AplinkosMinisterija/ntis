import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'bytesToText',
  standalone: true,
})
export class BytesToTextPipe implements PipeTransform {
  static readonly defaultPrecision = 2;
  static readonly defaultUnits = ['B', 'KB', 'MB', 'GB', 'TB', 'PB'];

  transform(
    bytes: number,
    precision: number = BytesToTextPipe.defaultPrecision,
    units = BytesToTextPipe.defaultUnits
  ): string {
    if (typeof precision !== 'number' || isNaN(precision) || !isFinite(precision) || precision < 0) {
      precision = BytesToTextPipe.defaultPrecision;
    }

    if (!units || units.length < 2) {
      units = BytesToTextPipe.defaultUnits;
    }

    const power = Math.min(Math.round(Math.log(bytes) / Math.log(1024)), units.length - 1);
    // const unitIndex = units[power];
    let unitIndex = power;
    let size = bytes / Math.pow(1024, power); // size in new units

    if (size < 0.5 && unitIndex > 0) {
      size = size * 1024;
      unitIndex = unitIndex - 1;
    }

    const roundMultiplier = precision > 0 ? Math.pow(10, precision) : 1;
    const roundedSize = Math.round(size * roundMultiplier) / roundMultiplier; // keep up to 2 decimals

    return `${roundedSize} ${units[unitIndex]}`;
  }
}
