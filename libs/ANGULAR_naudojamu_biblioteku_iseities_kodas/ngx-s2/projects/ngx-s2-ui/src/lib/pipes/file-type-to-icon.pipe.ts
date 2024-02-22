import { Injectable, Pipe, PipeTransform } from '@angular/core';
import { getMimeTypeClass } from '../utils/get-mime-type-class';

@Injectable({
  providedIn: 'root',
})
@Pipe({
  name: 'fileTypeToIcon',
  standalone: true,
})
export class FileTypeToIconPipe implements PipeTransform {
  static icons: Record<string, string> = {
    'application/geo+json': 'map',
    'application/json': 'data_object',
    'application/pdf': 'picture_as_pdf',
    'application/zip': 'folder_zip',
    'audio/*': 'music_note',
    'font/*': 'font_download',
    'image/*': 'imagesmode',
    'text/*': 'description',
    'text/css': 'css',
    'text/html': 'html',
    'text/javascript': 'javascript',
    'video/*': 'videocam',
  };

  transform(value: string, defaultIcon = 'draft'): string {
    return FileTypeToIconPipe.icons[value] || this.getWildcardType(value) || defaultIcon;
  }

  private getWildcardType(value: string): string {
    const wildcardTypeKey = Object.keys(FileTypeToIconPipe.icons)
      .filter((mimeType) => mimeType.endsWith('/*'))
      .find((mimeType) => getMimeTypeClass(mimeType) === getMimeTypeClass(value));
    return wildcardTypeKey && FileTypeToIconPipe.icons[wildcardTypeKey];
  }
}
