import { Pipe, PipeTransform } from '@angular/core';
import { SprFile } from '../model/api/api';

@Pipe({
  name: 'parseFileObject',
})
export class ParseFileObjectPipe implements PipeTransform {
  transform(value: string): SprFile[] {
    let data: SprFile[] = [];
    if (value?.length > 0) {
      data = JSON.parse(value) as SprFile[];
    }
    return data;
  }
}
