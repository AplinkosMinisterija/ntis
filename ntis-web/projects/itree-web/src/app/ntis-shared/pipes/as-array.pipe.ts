import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'asArray',
  standalone: true,
})
export class AsArrayPipe implements PipeTransform {
  transform(value: unknown): unknown[] {
    return value as unknown[];
  }
}
